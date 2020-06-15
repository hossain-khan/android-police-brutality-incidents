package com.blacklivesmatter.policebrutality.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo

/**
 * Class used to capture unique states and total incidents.
 *
 * See https://developer.android.com/training/data-storage/room/accessing-data#query-subset-cols
 */
@Keep
data class LocationIncidents constructor(
    @ColumnInfo(name = "state") val stateName: String,
    @ColumnInfo(name = "total_incidents") val totalIncidents: Int
) {
    val totalIncidentsText: String
        get() = totalIncidents.toString().trim()
}
