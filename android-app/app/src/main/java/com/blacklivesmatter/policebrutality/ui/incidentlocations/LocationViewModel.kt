package com.blacklivesmatter.policebrutality.ui.incidentlocations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacklivesmatter.policebrutality.api.IncidentApi
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository,
    private val incidentApi: IncidentApi
) : ViewModel() {

    val locations: LiveData<List<LocationIncidents>> = incidentRepository.getLocations()

    init {
        viewModelScope.launch {
            val incidents = incidentApi.getAllIncidents()
            Timber.d("Got all incidents $incidents") // TODO - remove, test call
        }
    }
}
