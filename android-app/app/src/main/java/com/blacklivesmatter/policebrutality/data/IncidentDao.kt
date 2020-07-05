package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents

/**
 * Data access object interface for Room.
 *
 * See:
 * - https://developer.android.com/training/data-storage/room
 * - https://developer.android.com/topic/libraries/architecture/room
 */
@Dao
interface IncidentDao {
    @Query("SELECT * FROM incidents ORDER BY name")
    fun getIncidents(): LiveData<List<Incident>>

    @Query(
        """SELECT incidents.state, COUNT(*) as total_incidents, MAX(incidents.date) AS last_reported_date 
        FROM incidents 
        GROUP BY state ORDER BY state ASC"""
    )
    fun getUniqueStates(): LiveData<List<LocationIncidents>>

    @Query("SELECT * FROM incidents WHERE state = :stateName ORDER BY date ASC")
    fun getIncidentsForState(stateName: String): LiveData<List<Incident>>

    @Query("SELECT * FROM incidents WHERE DATE(date) = DATE(DATETIME(:timestamp, 'unixepoch')) ORDER BY name")
    fun getIncidentsByDate(timestamp: Long): LiveData<List<Incident>>

    @Query("SELECT COUNT(id) FROM incidents")
    suspend fun getTotalRecords(): Int

    @Query("SELECT COUNT(*) as count FROM incidents where DATE(date) = DATE(DATETIME(:timestamp, 'unixepoch'))")
    fun getTotalIncidentsOnDate(timestamp: Long): LiveData<Int>

    @Query("SELECT DATE(date) as date_text FROM incidents GROUP BY DATE(date) ORDER BY date_text")
    fun getIncidentDates(): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(incidents: List<Incident>)

    @Query("DELETE FROM incidents WHERE id NOT IN (:ids)")
    suspend fun deleteItemByIds(ids: List<String>): Int
}
