package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blacklivesmatter.policebrutality.data.model.Incident

@Dao
interface IncidentDao {
    @Query("SELECT * FROM incidents ORDER BY name")
    fun getIncidents(): LiveData<List<Incident>>

    @Query("SELECT DISTINCT state FROM incidents ORDER BY state ASC")
    fun getUniqueStates(): LiveData<List<String>>

    @Query("SELECT * FROM incidents WHERE state = :stateName ORDER BY name")
    fun getIncidentsForState(stateName: String): LiveData<List<Incident>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Incident>)
}