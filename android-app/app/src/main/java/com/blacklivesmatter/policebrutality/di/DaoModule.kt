package com.blacklivesmatter.policebrutality.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.blacklivesmatter.policebrutality.config.DATABASE_NAME
import com.blacklivesmatter.policebrutality.data.AppDatabase
import com.blacklivesmatter.policebrutality.data.CharityDao
import com.blacklivesmatter.policebrutality.data.IncidentDao
import com.blacklivesmatter.policebrutality.worker.SeedDatabaseWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DaoModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        /**
         * Internal local function to create and pre-populate the database.
         * See this article for more details:
         * https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
         */
        fun populateDatabase(context: Context) {
            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
            WorkManager.getInstance(context).enqueue(request)
        }

        return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    populateDatabase(appContext)
                }

                override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                    super.onDestructiveMigration(db)
                    populateDatabase(appContext)
                }
            })
            // https://developer.android.com/training/data-storage/room/migrating-db-versions#kotlin
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideIncidentDao(database: AppDatabase): IncidentDao {
        return database.incidentDao()
    }

    @Provides
    fun provideCharityDao(database: AppDatabase): CharityDao {
        return database.charityDao()
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }
}
