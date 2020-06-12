package com.blacklivesmatter.policebrutality.ui.incident

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent
import timber.log.Timber
import javax.inject.Inject

class IncidentViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {
    internal val selectedState = ObservableField("")

    private val _incidents = MediatorLiveData<List<Incident>>()
    val incidents: LiveData<List<Incident>> = _incidents

    private val _shareIncident = LiveEvent<Incident>()
    val shareIncident: LiveData<Incident> = _shareIncident

    fun onShareIncidentClicked(incident: Incident) {
        Timber.d("User clicked on share incident")
        _shareIncident.value = incident
    }

    fun setArgs(navArgs: IncidentsFragmentArgs) {
        navArgs.stateName?.let { selectedSate(it) }
        if (navArgs.timestamp != 0L) {
            selectedTimestamp(navArgs.timestamp)
        }
    }

    private fun selectedSate(stateName: String) {
        selectedState.set(stateName)

        _incidents.addSource(incidentRepository.getStateIncidents(stateName)) {
            Timber.d("Incidents Updated ")
            _incidents.value = it
        }
    }

    private fun selectedTimestamp(timestamp: Long) {
        _incidents.addSource(incidentRepository.getIncidentsByDate(timestamp)) {
            Timber.d("Incidents Updated ")
            _incidents.value = it
        }
    }
}
