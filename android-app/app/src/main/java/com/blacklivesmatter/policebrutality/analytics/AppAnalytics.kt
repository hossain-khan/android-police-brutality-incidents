package com.blacklivesmatter.policebrutality.analytics

import android.app.Activity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Event
import com.google.firebase.analytics.FirebaseAnalytics.Event.SEARCH
import com.google.firebase.analytics.FirebaseAnalytics.Param.CONTENT_TYPE
import com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_ID
import com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_NAME
import com.google.firebase.analytics.FirebaseAnalytics.Param.SEARCH_TERM
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

/**
 * App analytics that uses [FirebaseAnalytics] as backbone.
 * See https://firebase.google.com/docs/analytics/get-started?platform=android
 */
class AppAnalytics @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {
    override fun logPageView(activity: Activity, screenName: String) {
        firebaseAnalytics.setCurrentScreen(activity, screenName, null)
    }

    override fun logSearch(searchTerm: String) {
        firebaseAnalytics.logEvent(SEARCH) {
            param(SEARCH_TERM, searchTerm)
        }
    }

    override fun logSelectItem(type: String, id: String, name: String) {
        firebaseAnalytics.logEvent(Event.SELECT_CONTENT) {
            param(ITEM_ID, id)
            param(ITEM_NAME, name)
            param(CONTENT_TYPE, type)
        }
    }

    override fun logShare(type: String, id: String) {
        firebaseAnalytics.logEvent(Event.SHARE) {
            param(ITEM_ID, id)
            param(CONTENT_TYPE, type)
        }
    }

    override fun logEvent(name: String) {
        firebaseAnalytics.logEvent(name, null)
    }
}
