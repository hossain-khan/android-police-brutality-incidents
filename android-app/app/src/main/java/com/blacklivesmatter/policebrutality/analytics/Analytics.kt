package com.blacklivesmatter.policebrutality.analytics

import android.app.Activity

/**
 * Interface to log events for the app.
 */
interface Analytics {
    companion object {
        const val SCREEN_REPORT_INCIDENT = "ReportNewIncident"
        const val SCREEN_INCIDENT_LOCATION = "IncidentLocations"
        const val SCREEN_INCIDENT_LIST_BY_DATE = "IncidentsByDate"
        const val SCREEN_INCIDENT_LIST_BY_LOCATION = "IncidentsByLocation"
        const val SCREEN_INCIDENT_DATE_FILTER = "FilterIncidentsByDate"
        const val SCREEN_MORE_INFO = "MoreInformation"
        const val SCREEN_ABOUT_APP = "AboutApplication"

        const val ACTION_INCIDENT_REFRESH = "RefreshIncidents"
        const val ACTION_INCIDENT_REPORT_NEW = "MakeIncidentReport"
    }

    /**
     * Log event when a user views a page.
     */
    fun logPageView(activity: Activity, screenName: String)

    /**
     * Log event when a user searches in the app
     */
    fun logSearch(searchTerm: String)

    /**
     * Log event when a user has selected content in an app
     */
    fun logSelectItem(type: String, id: String, name: String)

    /**
     * Log event when a user has shared content in an app
     */
    fun logShare(type: String, id: String)
}
