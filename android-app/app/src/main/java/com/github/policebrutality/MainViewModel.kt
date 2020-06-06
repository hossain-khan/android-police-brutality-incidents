package com.github.policebrutality

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.policebrutality.data.IncidentRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {
    init {
        Log.d("TAG", "Got incident repo: $incidentRepository")
    }
}