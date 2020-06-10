package com.blacklivesmatter.policebrutality.config

import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset

/*
 * All constants used throughout the app.
 */

/**
 * George Perry Floyd Jr. (October 14, 1973 – May 25, 2020)
 * Time zone in Minneapolis, MN, USA (GMT-5)
 *
 * - https://en.wikipedia.org/wiki/George_Floyd
 * - https://en.wikipedia.org/wiki/8%E2%80%B246%E2%80%B3
 */
val THE_846_DAY: OffsetDateTime = OffsetDateTime.of(/* year */ 2020, /* month */ 5, /* dayOfMonth */ 25,
    /* hour */ 8, /* minute */ 19, /* second */ 0, /* nanoOfSecond */ 0, ZoneOffset.of("-5")
)

const val DATABASE_NAME = "incidents-db"

/**
 * Fallback data file
 */
const val INCIDENT_DATA_FILENAME = "all_locations_fallback.json"

/**
 * NOTE: URL taken from https://github.com/2020PB/police-brutality/blob/master/README.md on June 8th, 2020
 */
const val REPORT_INCIDENT_WEB_URL = "https://github.com/2020PB/police-brutality/issues/new?" +
        "assignees=&labels=Incident+report&template=incident-report.md&title=Incident+in+CITY%2C+STATE"

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
