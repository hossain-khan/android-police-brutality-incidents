package com.blacklivesmatter.policebrutality.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import com.blacklivesmatter.policebrutality.ui.extensions.daysSinceToday
import com.blacklivesmatter.policebrutality.ui.extensions.toDateText
import org.threeten.bp.OffsetDateTime

/**
 * Class used to capture unique states and total incidents.
 *
 * See https://developer.android.com/training/data-storage/room/accessing-data#query-subset-cols
 */
@Keep
data class LocationIncidents constructor(
    @ColumnInfo(name = "state") val stateName: String,
    @ColumnInfo(name = "total_incidents") val totalIncidents: Int,
    @ColumnInfo(name = "last_reported_date") val lastReportedDate: OffsetDateTime?
) {
    val totalIncidentsText: String
        get() = totalIncidents.toString().trim()

    val lastReportedOn: String
        get() = lastReportedDate.toDateText()

    val daysSinceReport: Int
        get() = lastReportedDate.daysSinceToday()
}
