package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents

interface IncidentRepository {
    fun getIncidents(): LiveData<List<Incident>>
    fun getStateIncidents(state: String): LiveData<List<Incident>>
    fun getIncidentsByDate(timeStamp: Long): LiveData<List<Incident>>
    fun getLocations(): LiveData<List<LocationIncidents>>
    fun getTotalIncidentsOnDate(timeStamp: Long): LiveData<Int>

    /*
     * Based on return type table using live data provides us with lifecycle aware stream of data
     * https://developer.android.com/training/data-storage/room/accessing-data#return-types
     *
     * Hence, a different set of API is exposed here with coroutine functions
     * In future, this can be merged using Kotlin `Flow<T>`
     */
    suspend fun getIncidentsCoroutine(): List<Incident>
    suspend fun addIncidents(incidents: List<Incident>)
}
