package com.blacklivesmatter.policebrutality.ui.incidentlocations

import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    sealed class RefreshEvent {
        object Loading : RefreshEvent()
        object Success : RefreshEvent()
        data class Error(val exception: Exception) : RefreshEvent()
    }

    val isOperationInProgress = ObservableField(false)

    val locations: LiveData<List<LocationIncidents>> = incidentRepository.getLocations()

    private val _dateFilterMediatorEvent = MediatorLiveData<NavigationEvent>()
    private val _dateFilterEvent = LiveEvent<NavigationEvent>()
    val dateFilterEvent: LiveData<NavigationEvent> = _dateFilterEvent

    private val _refreshEvent = LiveEvent<RefreshEvent>()
    val refreshEvent: LiveData<RefreshEvent> = _refreshEvent

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

    fun onRefreshIncidentsRequested() {
        if (isOperationInProgress.get() == true) {
            Timber.w("Already loading content. Ignore additional refresh request.")
            return
        }

        isOperationInProgress.set(true)
        _refreshEvent.value = RefreshEvent.Loading
        Timber.d("Refresh requested")
        viewModelScope.launch {
            delay(2000)
            isOperationInProgress.set(false)
            val incidents: List<Incident> = incidentRepository.getIncidentsCoroutine()
            incidentRepository.addIncidents(incidents)
            _refreshEvent.value = RefreshEvent.Success
        }
    }
}
