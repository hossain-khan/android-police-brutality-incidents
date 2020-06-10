package com.blacklivesmatter.policebrutality.ui.incidentlocations

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {
    sealed class NavigationEvent {
        data class Filter(val timestamp: Long) : NavigationEvent()
        data class Error(val selectedDateText: String) : NavigationEvent()
    }

    val isOperationInProgress = ObservableField(false)

    val locations: LiveData<List<LocationIncidents>> = incidentRepository.getLocations()

    private val _dateFilterMediatorEvent = MediatorLiveData<NavigationEvent>()
    val dateFilterEvent: LiveData<NavigationEvent> = _dateFilterMediatorEvent

    fun onDateTimeStampSelected(selectedTimeStamp: Long) {
        // Check if the date range has any records
        val timeStamp = TimeUnit.MILLISECONDS.toSeconds(selectedTimeStamp)
        val totalIncidentsOnDate = incidentRepository.getTotalIncidentsOnDate(timeStamp)

        _dateFilterMediatorEvent.addSource(totalIncidentsOnDate) {
            if (it > 0) {
                _dateFilterMediatorEvent.value = NavigationEvent.Filter(timeStamp)
            } else {
                val dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                val instant = Instant.ofEpochMilli(selectedTimeStamp)
                val offsetDateTime: OffsetDateTime = OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"))

                _dateFilterMediatorEvent.value = NavigationEvent.Error(offsetDateTime.format(dateTimeFormatter))
            }
        }
    }
}
