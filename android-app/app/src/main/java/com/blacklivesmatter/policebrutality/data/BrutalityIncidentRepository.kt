package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import com.blacklivesmatter.policebrutality.api.IncidentApi
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import javax.inject.Inject

class BrutalityIncidentRepository @Inject constructor(
    private val incidentDao: IncidentDao,
    private val incidentApi: IncidentApi
) : IncidentRepository {
    override fun getIncidents(): LiveData<List<Incident>> {
        return incidentDao.getIncidents()
    }

    override fun getStateIncidents(state: String): LiveData<List<Incident>> {
        return incidentDao.getIncidentsForState(state)
    }

    override fun getLocations(): LiveData<List<LocationIncidents>> {
        return incidentDao.getUniqueStates()
    }

    override fun getTotalIncidentsOnDate(timeStamp: Long): LiveData<Int> {
        return incidentDao.getTotalIncidentsOnDate(timeStamp)
    }
}
