package com.blacklivesmatter.policebrutality.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blacklivesmatter.policebrutality.data.model.Incident

/**
 * The Room database for this app
 */
@Database(entities = [Incident::class], version = 4, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidentDao(): IncidentDao
}
