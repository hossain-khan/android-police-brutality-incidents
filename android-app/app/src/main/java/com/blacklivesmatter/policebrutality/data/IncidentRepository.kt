package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents

interface IncidentRepository {
    fun getIncidents(): LiveData<List<Incident>>
    fun getStateIncidents(state: String): LiveData<List<Incident>>
    fun getLocations(): LiveData<List<LocationIncidents>>
    fun getTotalIncidentsOnDate(timeStamp: Long): LiveData<Int>
}
