package com.github.policebrutality.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.policebrutality.config.DATABASE_NAME
import com.github.policebrutality.data.model.Incident
import com.github.policebrutality.worker.SeedDatabaseWorker

/**
 * The Room database for this app
 */
@Database(entities = [Incident::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidentDao(): IncidentDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }


        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        populateDatabase(context)
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        populateDatabase(context)
                    }
                })
                // https://developer.android.com/training/data-storage/room/migrating-db-versions#kotlin
                .fallbackToDestructiveMigration()
                .build()
        }

        /**
         * Create and pre-populate the database. See this article for more details:
         * https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
         */
        private fun populateDatabase(context: Context) {
            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}
