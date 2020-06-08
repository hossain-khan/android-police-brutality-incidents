package com.github.policebrutality.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.policebrutality.data.IncidentRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {

    val states: LiveData<List<String>> = incidentRepository.getStates()
}