package com.github.policebrutality.ui.incident

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.policebrutality.data.IncidentRepository
import com.github.policebrutality.data.model.Incident
import javax.inject.Inject

class IncidentViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {
    internal val selectedState = ObservableField("")

    // TODO - use state specific incidents
    val incidents: LiveData<List<Incident>> = incidentRepository.getIncidents()

    fun selectedSate(stateName: String) {
        selectedState.set(stateName)
    }
}