package com.blacklivesmatter.policebrutality.ui.incidentlocations

import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val incidentRepository: IncidentRepository
) : ViewModel() {
    sealed class NavigationEvent {
        data class Filter(val timestamp: Long, val dateText: String) : NavigationEvent()
        data class Error(val selectedDateText: String) : NavigationEvent()
    }

    val isOperationInProgress = ObservableField(false)

    val locations: LiveData<List<LocationIncidents>> = incidentRepository.getLocations()

    private val _dateFilterMediatorEvent = MediatorLiveData<NavigationEvent>()
    private val _dateFilterEvent = LiveEvent<NavigationEvent>()
    val dateFilterEvent: LiveData<NavigationEvent> = _dateFilterEvent

    fun onDateTimeStampSelected(lifecycleOwner: LifecycleOwner, selectedTimeStamp: Long) {
        // Check if the date range has any records
        val timeStamp = TimeUnit.MILLISECONDS.toSeconds(selectedTimeStamp)
        val totalIncidentsOnDate = incidentRepository.getTotalIncidentsOnDate(timeStamp)

        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
        val instant = Instant.ofEpochMilli(selectedTimeStamp)
        val offsetDateTime: OffsetDateTime = OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"))
        val selectedDateText = offsetDateTime.format(dateTimeFormatter)

        _dateFilterMediatorEvent.addSource(totalIncidentsOnDate) {
            if (it > 0) {
                _dateFilterEvent.value = NavigationEvent.Filter(timeStamp, selectedDateText)
            } else {
                _dateFilterEvent.value = NavigationEvent.Error(selectedDateText)
            }
        }

        _dateFilterMediatorEvent.observe(lifecycleOwner, Observer {
            Timber.d("Got navigation event $it (ignored here)")
        })
    }
}
