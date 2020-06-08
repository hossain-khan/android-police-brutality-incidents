package com.blacklivesmatter.policebrutality.data

import androidx.lifecycle.LiveData
import com.blacklivesmatter.policebrutality.data.model.Incident
import javax.inject.Inject

class BrutalityIncidentRepository @Inject constructor(private val dao: IncidentDao) : IncidentRepository {
    override fun getIncidents(): LiveData<List<Incident>> {
        return dao.getIncidents()
    }

    override fun getStateIncidents(state: String): LiveData<List<Incident>> {
        return dao.getIncidentsForState(state)
    }

    override fun getStates(): LiveData<List<String>> {
        return dao.getUniqueStates()
    }
}