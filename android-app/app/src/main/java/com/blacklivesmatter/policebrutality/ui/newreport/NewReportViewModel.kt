package com.blacklivesmatter.policebrutality.ui.newreport

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.config.REPORT_INCIDENT_WEB_URL
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent

class NewReportViewModel @ViewModelInject constructor(
    private val analytics: Analytics
) : ViewModel() {
    private val _openReportIncidentUrl = LiveEvent<String>()
    val openReportIncidentUrl: LiveData<String> = _openReportIncidentUrl

    fun onReportIncidentClicked() {
        analytics.logEvent(Analytics.ACTION_INCIDENT_REPORT_NEW)
        _openReportIncidentUrl.value = REPORT_INCIDENT_WEB_URL
    }
}
