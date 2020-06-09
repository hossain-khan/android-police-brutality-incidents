package com.blacklivesmatter.policebrutality.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * An example data exposed from JSON response.
 *
 * Sample data:
 *
 * ```
 * {
 *    "links":[
 *       "https://twitter.com/courtenay_roche/status/1267653137969623040",
 *       "https://twitter.com/yagirlbrookie09/status/1267647898365427714",
 *       "https://www.4029tv.com/article/bentonville-police-deploy-tear-gas-on-protesters/32736629#"
 *    ],
 *    "state":"Arkansas",
 *    "edit_at":"https://github.com/2020PB/police-brutality/blob/master/reports/Arkansas.md",
 *    "city":"Bentonville",
 *    "name":"Law enforcement gas a crowd chanting \u201cwe want peace\u201d right after exiting the building.",
 *    "date":"2020-06-01",
 *    "date_text":"June 1st",
 *    "id": "ar-bentonville-1"
 * }
 * ```
 */
@Keep
@Entity(tableName = "incidents")
data class Incident(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val _id: Long,
    @SerializedName("id") @ColumnInfo(name = "incident_id") val incident_id: String? = "",
    @ColumnInfo(name = "state") val state: String? = "",
    @ColumnInfo(name = "edit_url") val edit_at: String? = "",
    @ColumnInfo(name = "city") val city: String? = "",
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "date") val date: String? = "", // TODO - should use proper date to be able to use it as filter
    @ColumnInfo(name = "date_text") val date_text: String? = "",
    @ColumnInfo(name = "links") val links: List<String> = emptyList()
)
