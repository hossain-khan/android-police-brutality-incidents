package com.blacklivesmatter.policebrutality.ui.util

import android.net.Uri
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.data.model.LinkInfo

object LinkTransformer {
    /**
     * Convenience map to get drawable resource icon for respective web domain name.
     */
    private val socialIcons = mapOf(
        "www.instagram.com" to R.drawable.ic_web_instagram,
        "www.facebook.com" to R.drawable.ic_web_facebook,
        "twitter.com" to R.drawable.ic_web_twitter,
        "mobile.twitter.com" to R.drawable.ic_web_twitter,
        "youtu.be" to R.drawable.ic_web_youtube,
        "www.youtube.com" to R.drawable.ic_web_youtube,
        "v.redd.it" to R.drawable.ic_web_reddit,
        "old.reddit.com" to R.drawable.ic_web_reddit,
        "www.reddit.com" to R.drawable.ic_web_reddit
    ).withDefault { R.drawable.ic_outline_web }

    /**
     * Converts links to link info object with respective data.
     *
     * For example:
     * ```
     * - https://twitter.com/chadloder/status/1267314138428014594
     *   --> "Twitter"
     * - https://v.redd.it/9aiytt50g6251/DASH_1080#mp4
     *   --> "Reddit"
     * - https://old.reddit.com/r/PublicFreakout/comments/gu8mqp/police_drives_into_protestors_in_los_angeles/
     *   --> "Reddit"
     * - https://abc7.com/deputies-shoot-pepper-balls-at-skateboarders-in-hollywood---video/6230652/
     *   --> abc7.com
     * - https://www.theguardian.com/us-news/2020/jun/04/vallejo-police-kill-unarmed-man-california
     *   --> theguardian.com
     * - https://youtu.be/pRmBO34aXME
     *   --> YouTube
     * ```
     */
    fun toLinkInfo(link: String): LinkInfo {
        val linkUri = Uri.parse(link)

        return LinkInfo(
            sourceLink = link,
            name = linkUri.authority?.replace("www.", "") ?: "External Link",
            iconResId = socialIcons.getValue(linkUri.authority.toString())
        )
    }
}