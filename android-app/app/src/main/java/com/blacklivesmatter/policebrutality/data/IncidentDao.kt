package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents

@Dao
interface IncidentDao {
    @Query("SELECT * FROM incidents ORDER BY name")
    fun getIncidents(): LiveData<List<Incident>>

    @Query("SELECT state, COUNT(*) as total_incidents FROM incidents GROUP BY state ORDER BY state ASC")
    fun getUniqueStates(): LiveData<List<LocationIncidents>>

    @Query("SELECT * FROM incidents WHERE state = :stateName ORDER BY name")
    fun getIncidentsForState(stateName: String): LiveData<List<Incident>>

    @Query("SELECT COUNT(_id) FROM incidents")
    fun getTotalRecords(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Incident>)
}
