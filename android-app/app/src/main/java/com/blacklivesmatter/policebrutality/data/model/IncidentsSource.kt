package com.blacklivesmatter.policebrutality.data.model

import androidx.annotation.Keep

/**
 * Container object for list of [Incident]s.
 * Example data:
 * ```
 * {
 *    "edit_at":"https://github.com/2020PB/police-brutality",
 *    "help":"ask @ubershmekel on twitter",
 *    "updated_at":"2020-06-06T10:35:46.054104+00:00",
 *    "data":[ {...}, {...}, {...}, {...}]
 * }
 * ```
 */
@Keep
data class IncidentsSource constructor(
    val edit_at: String = "",
    val help: String = "",
    val updated_at: String = "",
    val data: List<Incident> = emptyList()
)
