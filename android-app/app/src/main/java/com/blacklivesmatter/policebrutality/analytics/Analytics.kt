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
        const val SCREEN_CHARITY_ORGANIZATIONS = "CharityOrganizations"

        //
        // Based on existing firebase analytics action naming convention, using `_` and all lowercase
        // https://support.google.com/firebase/answer/6317498
        //
        const val ACTION_INCIDENT_REFRESH = "incidents_refresh"
        const val ACTION_INCIDENT_REPORT_NEW = "incidents_report_new"
        const val ACTION_SHARE_APP = "app_share"
        const val ACTION_CHARITY_DONATE = "charity_donate"
        const val ACTION_CHARITY_DONATE_INFO = "charity_org_info"

        const val CONTENT_TYPE_LOCATION = "TypeLocation"
        const val CONTENT_TYPE_CHARITY = "TypeCharity"
        const val CONTENT_TYPE_INCIDENT = "TypeIncident"
        const val CONTENT_TYPE_INCIDENT_SHARE = "TypeShareIncident"
        const val CONTENT_TYPE_HASHTAG = "TypeHashtag"
        const val CONTENT_TYPE_PB2020_LINK = "TypePBLink"
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

    /**
     * Log event when a user action is taken.
     */
    fun logEvent(name: String)
}
