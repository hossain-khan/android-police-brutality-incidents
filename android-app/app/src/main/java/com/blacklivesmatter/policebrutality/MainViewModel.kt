package com.blacklivesmatter.policebrutality

import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import javax.inject.Inject
import timber.log.Timber

class MainViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {
    init {
        Timber.d("Got incident repo: $incidentRepository")
    }
}
