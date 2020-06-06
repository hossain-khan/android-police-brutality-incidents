package com.github.policebrutality.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.policebrutality.data.model.Incident

@Dao
interface IncidentDao {
    @Query("SELECT * FROM incidents ORDER BY name")
    fun getIncidents(): LiveData<List<Incident>>

    @Query("SELECT * FROM incidents WHERE state = :stateName ORDER BY name")
    fun getIncidentsForState(stateName: String): LiveData<List<Incident>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Incident>)
}