package com.blacklivesmatter.policebrutality.config

import com.blacklivesmatter.policebrutality.data.AppDatabase
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

/*
 * All constants used throughout the app.
 */

/**
 * George Perry Floyd Jr. (October 14, 1973 – May 25, 2020)
 * Time zone in Minneapolis, MN, USA (GMT-5)
 *
 * - https://en.wikipedia.org/wiki/George_Floyd
 * - https://en.wikipedia.org/wiki/8%E2%80%B246%E2%80%B3 (aka 8′46″)
 */
val THE_846_DAY: OffsetDateTime by lazy {
    OffsetDateTime.of(
        /* year */ 2020, /* month */ 5, /* dayOfMonth */ 25,
        /* hour */ 8, /* minute */ 19, /* second */ 0,
        /* nanoOfSecond */ 0, ZoneOffset.of("-5")
    )
}

val TODAY: OffsetDateTime by lazy {
    OffsetDateTime.now()
}

/**
 * Database name for storing incidents locally.
 * See [AppDatabase].
 */
const val DATABASE_NAME = "incidents-db"

/**
 * Number of maximum incidents to load when showing latest incidents.
 */
const val LATEST_INCIDENT_LIMIT = 50

/**
 * Fallback data file incidents, used to preload [AppDatabase].
 */
const val INCIDENT_DATA_FILENAME = "all_locations_fallback.json"

/**
 * Bundled charity list data.
 *
 * See following links for sources:
 * - https://github.com/amardeshbd/android-police-brutality-incidents/tree/develop/resources#source-for-charitable-organization
 */
const val CHARITY_DATA_FILENAME = "donate_for_cause.json"

/**
 * NOTE: URL taken from https://github.com/2020PB/police-brutality/blob/master/README.md on June 8th, 2020
 */
const val REPORT_INCIDENT_WEB_URL = "https://github.com/2020PB/police-brutality/issues/new?" +
        "assignees=&labels=Incident+report&template=incident-report.md&title=Incident+in+CITY%2C+STATE"

/**
 * Number of days after when the incident data will auto refresh.
 */
const val INCIDENT_DATA_AUTO_REFRESH_DAYS = 14

/*
 * Relevant Links taken from https://www.reddit.com/r/2020PoliceBrutality/comments/gwxgsa/i_just_wanted_to_take_a_moment_to_share_with_you/
 * -----------------
 * - Github Repository: https://github.com/2020PB/police-brutality/blob/master/CONTRIBUTING.md#How-to-Contribute-1
 * - Reddit: https://www.reddit.com/r/2020PoliceBrutality/
 * - Twitter: https://twitter.com/2020police
 * - Instagram: https://www.instagram.com/r2020policebrutality
 * - Facebook: https://www.facebook.com/R2020PoliceBrutality
 * - Website: https://2020policebrutality.netlify.app/
 * - https://en.wikipedia.org/wiki/Copwatch
 */
const val PB_LINK_FACEBOOK = "https://www.facebook.com/R2020PoliceBrutality"
const val PB_LINK_INSTAGRAM = "https://www.instagram.com/r2020policebrutality"
const val PB_LINK_TWITTER = "https://twitter.com/2020police"
const val PB_LINK_WEB = "https://2020policebrutality.netlify.app/"
const val PB_LINK_REDDIT = "https://www.reddit.com/r/2020PoliceBrutality/"

//
// Shared preference keys
//
const val PREF_KEY_LAST_UPDATED_TIMESTAMP_EPOCH_SECONDS = "preference_key_last_updated_timestamp"
const val PREF_KEY_CHARITY_LIST_DISCLAIMER_INFO_SHOWN = "preference_key_charity_disclaimer_shown"
const val PREF_KEY_SHARE_CAPABILITY_REMINDER_SHOWN = "preference_key_share_incident_guide_shown"
