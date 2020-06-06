package com.github.policebrutality.data

import androidx.lifecycle.LiveData
import com.github.policebrutality.data.model.Incident
import javax.inject.Inject

class BrutalityIncidentRepository @Inject constructor(private val dao: IncidentDao) : IncidentRepository {
    override fun getIncidents(): LiveData<List<Incident>> {
        return dao.getIncidents()
    }
}