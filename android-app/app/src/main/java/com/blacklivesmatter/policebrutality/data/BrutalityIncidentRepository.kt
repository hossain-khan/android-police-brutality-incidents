package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import com.blacklivesmatter.policebrutality.api.IncidentApi
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import timber.log.Timber
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

    override fun getIncidentsByDate(timeStamp: Long): LiveData<List<Incident>> {
        return incidentDao.getIncidentsByDate(timeStamp)
    }

    override fun getLocations(): LiveData<List<LocationIncidents>> {
        return incidentDao.getUniqueStates()
    }

    override fun getTotalIncidentsOnDate(timeStamp: Long): LiveData<Int> {
        return incidentDao.getTotalIncidentsOnDate(timeStamp)
    }

    override suspend fun getIncidentsCoroutine(): List<Incident> {
        val source = incidentApi.getAllIncidents()
        return source.data
    }

    override suspend fun addIncidents(incidents: List<Incident>) {
        Timber.i("Adding/updating ${incidents.size} incidents.")
        incidentDao.insertAll(incidents)
    }
}
