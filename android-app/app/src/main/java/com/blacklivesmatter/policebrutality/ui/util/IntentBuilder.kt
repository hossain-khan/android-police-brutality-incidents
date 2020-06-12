package com.blacklivesmatter.policebrutality.ui.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.blacklivesmatter.policebrutality.config.PB_LINK_WEB
import com.blacklivesmatter.policebrutality.data.model.Incident

object IntentBuilder {
    // Google Play Application IDs that allows to launch appropriate app without having chooser intent.
    // Any "APPID" defined here should resolve to valid content using following URL:
    // https://play.google.com/store/apps/details?id=[APPID]

    private const val APP_ID_FACEBOOK = "com.facebook.katana"
    private const val APP_ID_INSTAGRAM = "com.instagram.android"
    private const val APP_ID_REDDIT = "com.reddit.frontpage"
    private const val APP_ID_TWITTER = "com.twitter.android"
    private const val APP_ID_YOUTUBE = "com.google.android.youtube"

    /**
     * Map of URI authority(domain) to Android App ID
     */
    private val appIdMap = mapOf<String, String>(
        "www.instagram.com" to APP_ID_INSTAGRAM,
        "www.facebook.com" to APP_ID_FACEBOOK,
        "twitter.com" to APP_ID_TWITTER,
        "mobile.twitter.com" to APP_ID_TWITTER,
        "youtu.be" to APP_ID_YOUTUBE,
        "www.youtube.com" to APP_ID_YOUTUBE,
        "v.redd.it" to APP_ID_REDDIT,
        "old.reddit.com" to APP_ID_REDDIT,
        "www.reddit.com" to APP_ID_REDDIT
    )

    /**
     * Creates a intent to open external web URL
     * See: https://developer.android.com/guide/components/intents-common#ViewUrl
     */
    fun build(context: Context, link: String): Intent? {
        val webPageUri: Uri = Uri.parse(link)
        val plainIntent = Intent(Intent.ACTION_VIEW, webPageUri)
        val intent = Intent(Intent.ACTION_VIEW, webPageUri)
        val appId: String? = appIdMap[webPageUri.authority]

        if (appId != null) {
            intent.setPackage(appId)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            // Try to use the package based intent first that opens the app directly
            return intent
        }

        if (plainIntent.resolveActivity(context.packageManager) != null) {
            // As fallback use vanilla intent that can be used by web browsers
            return plainIntent
        }

        // Finally if nothing works, return null as error indication
        return null
    }

    /**
     * Builds intent to share [incident] via email or other social network
     *
     * See: https://developer.android.com/training/sharing/send#send-text-content
     */
    fun share(incident: Incident): Intent {
        // First, build the share texts
        val shareContentTitle = "Check this incident that was reported at $PB_LINK_WEB"

        val shareBodyText = """
$shareContentTitle

- Incident: ${incident.name}
- Location: ${incident.city}, ${incident.state}
- Date: ${incident.date}

Reference/Evidence Links:
${incident.links.joinToString(separator = " \n * ", prefix = " * ")}
        """.trimIndent()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareBodyText)
            putExtra(Intent.EXTRA_SUBJECT, shareContentTitle)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        return shareIntent
    }
}
