package com.blacklivesmatter.policebrutality.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Container object for list of [Incident]s.
 * Example data:
 * ```
 * {
 *    "meta": { "about": "...", "more": "...", "get_involved": "..."},
 *    "data":[ {...}, {...}, {...}, {...}]
 * }
 * ```
 *
 * ### See
 * - [Sample API response](https://api.846policebrutality.com/api/incidents)
 */
@Keep
data class IncidentsSource constructor(
    @SerializedName("data") val data: List<Incident> = emptyList()
)
