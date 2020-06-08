package com.blacklivesmatter.policebrutality.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {

    val states: LiveData<List<String>> = incidentRepository.getStates()
}