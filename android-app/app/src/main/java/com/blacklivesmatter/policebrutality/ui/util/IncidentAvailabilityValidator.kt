package com.blacklivesmatter.policebrutality.ui.util

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.util.Locale

/**
 * Validates if given date has incident records.
 */
class IncidentAvailabilityValidator(
    /**
     * List of date that has available incident records
     */
    private val datesIncidentHappened: List<String>
) : CalendarConstraints.DateValidator {
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    override fun isValid(date: Long): Boolean {
        val instant = Instant.ofEpochMilli(date)
        val offsetDateTime: OffsetDateTime = OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"))
        val formattedDate = offsetDateTime.format(dateFormat)

        val didIncidentHappenOnThisDay = datesIncidentHappened.contains(formattedDate)
        Timber.d("Did incident happen on $formattedDate : $didIncidentHappenOnThisDay")
        return didIncidentHappenOnThisDay
    }

    /** Part of [android.os.Parcelable] requirements. Do not use. */
    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeList(datesIncidentHappened)
    }

    companion object CREATOR : Parcelable.Creator<IncidentAvailabilityValidator> {
        override fun createFromParcel(parcel: Parcel): IncidentAvailabilityValidator {
            return IncidentAvailabilityValidator(emptyList())
        }

        override fun newArray(size: Int): Array<IncidentAvailabilityValidator?> {
            return arrayOfNulls(size)
        }
    }
}
