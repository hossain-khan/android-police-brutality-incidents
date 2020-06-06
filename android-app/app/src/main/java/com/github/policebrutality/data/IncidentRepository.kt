package com.github.policebrutality.data

import androidx.lifecycle.LiveData
import com.github.policebrutality.data.model.Incident

interface IncidentRepository {
    fun getIncidents(): LiveData<List<Incident>>
}