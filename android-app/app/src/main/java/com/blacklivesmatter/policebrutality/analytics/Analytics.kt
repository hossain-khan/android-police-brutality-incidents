package com.blacklivesmatter.policebrutality.analytics

import android.app.Activity

/**
 * Interface to log events for the app.
 */
interface Analytics {
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
