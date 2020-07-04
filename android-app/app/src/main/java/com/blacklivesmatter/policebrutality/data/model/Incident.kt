package com.blacklivesmatter.policebrutality.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.blacklivesmatter.policebrutality.config.THE_846_DAY
import com.google.gson.annotations.SerializedName
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * An example data exposed from JSON response.
 *
 * Sample data:
 * ```
 * {
 *    "id":"7b044f80-a9d6-11ea-823c-cb6b88d56860",
 *    "pb_id":"ar-bentonville-1",
 *    "state":"Arkansas",
 *    "city":"Bentonville",
 *    "date":"2020-06-01T00:00:00.000000Z",
 *    "title":"Law enforcement gas a crowd chanting \u201cwe want peace\u201d right after exiting the building.",
 *    "description":null,
 *    "links":[
 *       "https:\/\/twitter.com\/courtenay_roche\/status\/1267653137969623040",
 *       "https:\/\/twitter.com\/yagirlbrookie09\/status\/1267647898365427714",
 *       "https:\/\/www.4029tv.com\/article\/bentonville-police-deploy-tear-gas-on-protesters\/32736629#"
 *    ],
 *    "data":null,
 *    "geocoding":{
 *       "lat":"36.3728538",
 *       "long":"-94.2088172"
 *    }
 * }
 * ```
 *
 * ### See
 * - [Sample API response](https://api.846policebrutality.com/api/incidents)
 * - [Support date+time in Room DB](https://medium.com/androiddevelopers/room-time-2b4cf9672b98)
 */
@Keep
@Entity(tableName = "incidents")
data class Incident(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") @ColumnInfo(name = "id") val id: String,
    @SerializedName("pb_id") @ColumnInfo(name = "incident_id", index = true) val incident_id: String? = "",
    @SerializedName("state") @ColumnInfo(name = "state") val state: String? = "",
    @SerializedName("city") @ColumnInfo(name = "city") val city: String? = "",
    @SerializedName("title") @ColumnInfo(name = "name") val name: String? = "",
    @SerializedName("date") @ColumnInfo(name = "date") val date: OffsetDateTime? = null,
    @SerializedName("geocoding") @ColumnInfo(name = "geocoding") val geocoding: GeoCoding?,
    @SerializedName("links") @ColumnInfo(name = "links") val links: List<String> = emptyList()
) {
    @Ignore
    private val unknownDateText = "Unknown Date"

    val dateText: String
        get() {
            return date?.let { incidentDate ->
                if (incidentDate.isBefore(THE_846_DAY)) {
                    return@let unknownDateText
                } else {
                    return@let incidentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
                }
            } ?: unknownDateText
        }

    /**
     * Validates if given geocoding data is usable and valid coordinates.
     *
     * DEV NOTE: Based on app usage, it seems like ~all the data is valid. Not sure if we need this validation.
     */
    val hasValidGeocodingData: Boolean
        get() {
            val lat: Double? = geocoding?.lat?.toDoubleOrNull()
            val long: Double? = geocoding?.long?.toDoubleOrNull()
            return geocoding?.lat != null &&
                    geocoding.long != null &&
                    lat != null &&
                    long != null &&
                    UsaGeoBounds.within(lat, long)
        }

    /**
     * http://en.wikipedia.org/wiki/Extreme_points_of_the_United_States
     */
    private object UsaGeoBounds {
        private const val left: Double = -124.7844079 // west long
        private const val right: Double = -66.9513812 // east long
        private const val top: Double = 49.3457868 // north lat
        private const val bottom: Double = 24.7433195 // south lat

        /**
         * Validates if give geo coordinates is within USA geo bounds defined in [UsaGeoBounds].
         */
        internal fun within(lat: Double, long: Double): Boolean {
            return lat in bottom..top && long in left..right
        }
    }
}
