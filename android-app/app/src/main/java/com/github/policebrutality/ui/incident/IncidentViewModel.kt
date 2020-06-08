package com.github.policebrutality.ui.incident

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.github.policebrutality.data.IncidentRepository
import com.github.policebrutality.data.model.Incident
import timber.log.Timber
import javax.inject.Inject

class IncidentViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {
    internal val selectedState = ObservableField("")

    private val _incidents = MediatorLiveData<List<Incident>>()
    val incidents: LiveData<List<Incident>> = _incidents

    fun selectedSate(stateName: String) {
        selectedState.set(stateName)

        _incidents.addSource(incidentRepository.getStateIncidents(stateName)) {
            Timber.d("Incidents Updated ")
            _incidents.value = it
        }
    }
}