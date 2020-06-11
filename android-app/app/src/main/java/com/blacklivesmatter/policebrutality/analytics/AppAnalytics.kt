package com.blacklivesmatter.policebrutality.analytics

import android.app.Activity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
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
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SEARCH_TERM, searchTerm)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle)
    }

    override fun logSelectItem(type: String, id: String, name: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, id)
            putString(FirebaseAnalytics.Param.ITEM_NAME, name)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, type)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    override fun logShare(type: String, id: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, id)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, type)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle)
    }

    override fun logEvent(name: String) {
        firebaseAnalytics.logEvent(name, null)
    }
}
