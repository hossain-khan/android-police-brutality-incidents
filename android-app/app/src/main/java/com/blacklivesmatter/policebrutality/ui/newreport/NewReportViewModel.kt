package com.blacklivesmatter.policebrutality.ui.newreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.config.REPORT_INCIDENT_WEB_URL
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent
import javax.inject.Inject

class NewReportViewModel @Inject constructor() : ViewModel() {
    private val _openReportIncidentUrl = LiveEvent<String>()
    val openReportIncidentUrl: LiveData<String> = _openReportIncidentUrl

    fun onReportIncidentClicked() {
        _openReportIncidentUrl.value = REPORT_INCIDENT_WEB_URL
    }
}
