package com.blacklivesmatter.policebrutality.ui.incidentlocations

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.config.INCIDENT_DATA_AUTO_REFRESH_DAYS
import com.blacklivesmatter.policebrutality.config.PREF_KEY_LAST_UPDATED_TIMESTAMP_EPOCH_SECONDS
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import com.blacklivesmatter.policebrutality.ui.extensions.LiveEvent
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * ViewModel for [LocationFragment].
 */
class LocationViewModel @ViewModelInject constructor(
    private val incidentRepository: IncidentRepository,
    private val analytics: Analytics,
    private val preferences: SharedPreferences
) : ViewModel(), LifecycleObserver {
    sealed class NavigationEvent {
        data class Filter(val timestamp: Long, val dateText: String) : NavigationEvent()
        data class Location(val stateName: String) : NavigationEvent()
        object LatestIncidents : NavigationEvent()
        data class Error(val selectedDateText: String) : NavigationEvent()
    }

    sealed class RefreshEvent {
        object Loading : RefreshEvent()
        data class Success(val totalItems: Int) : RefreshEvent()
        data class Error(val exception: Exception) : RefreshEvent()
    }

    val isOperationInProgress = ObservableField(false)

    val locations: LiveData<List<LocationIncidents>> = incidentRepository.getLocations()
    val incidentDates: LiveData<List<String>> = incidentRepository.getIncidentDates()

    private val _dateFilterMediatorEvent = MediatorLiveData<NavigationEvent>()
    private val _navigationEvent = LiveEvent<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent

    private val _refreshEvent = LiveEvent<RefreshEvent>()
    val refreshEvent: LiveData<RefreshEvent> = _refreshEvent

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onViewStarted() {
        if (shouldRequestLatestData()) {
            onRefreshIncidentsRequested()
        }
    }

    fun onIncidentLocationSelected(locationIncident: LocationIncidents) {
        Timber.d("Tapped on state item $locationIncident")
        analytics.logSelectItem(Analytics.CONTENT_TYPE_LOCATION, locationIncident.stateName, locationIncident.stateName)
        _navigationEvent.value = NavigationEvent.Location(stateName = locationIncident.stateName)
    }

    fun onShowLatestIncidentsSelected() {
        analytics.logEvent(Analytics.ACTION_INCIDENT_FILTER_RECENT)
        _navigationEvent.value = NavigationEvent.LatestIncidents
    }

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
                _navigationEvent.value = NavigationEvent.Filter(timeStamp, selectedDateText)
            } else {
                _navigationEvent.value = NavigationEvent.Error(selectedDateText)
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
        analytics.logEvent(Analytics.ACTION_INCIDENT_REFRESH)

        isOperationInProgress.set(true)
        _refreshEvent.value = RefreshEvent.Loading
        Timber.d("Refresh requested")
        viewModelScope.launch {
            isOperationInProgress.set(false)
            val incidents: List<Incident> = try {
                incidentRepository.getIncidentsCoroutine()
            } catch (error: Exception) {
                Timber.e(error, "Unable to process API request.")
                emptyList()
            }

            if (incidents.isEmpty()) {
                // Something went wrong, DO NOT proceed
                _refreshEvent.value = RefreshEvent.Error(IllegalStateException("Unable to refresh content"))
                return@launch
            }

            Timber.d("Received total ${incidents.size} incidents, updating local cache.")
            saveLastUpdatedTimestamp()

            incidentRepository.addIncidents(incidents)
            incidentRepository.removeStaleIncidents(incidents)
            _refreshEvent.value = RefreshEvent.Success(incidents.size)
        }
    }

    internal fun shouldRequestLatestData(): Boolean {
        val lastUpdatedTimestamp = preferences.getLong(PREF_KEY_LAST_UPDATED_TIMESTAMP_EPOCH_SECONDS, 0)

        val rightNow = LocalDateTime.now()
        val lastRefreshTime = LocalDateTime.ofEpochSecond(lastUpdatedTimestamp, 0, ZoneOffset.UTC)
        val daysSinceLastRefresh = Duration.between(lastRefreshTime, rightNow).toDays()

        val shouldRefresh = daysSinceLastRefresh > INCIDENT_DATA_AUTO_REFRESH_DAYS
        Timber.i("It has been %d days since last updated. Refreshing: %b", daysSinceLastRefresh, shouldRefresh)

        return shouldRefresh
    }

    private fun saveLastUpdatedTimestamp() {
        Timber.d("Updating last updated timestamp to just now.")
        preferences.edit {
            putLong(PREF_KEY_LAST_UPDATED_TIMESTAMP_EPOCH_SECONDS, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        }
    }
}
