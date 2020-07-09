/*
 * MIT License
 *
 * Copyright (c) 2020 Hossain Khan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.blacklivesmatter.policebrutality.ui.incidentlocations

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.Observer
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.config.INCIDENT_DATA_AUTO_REFRESH_DAYS
import com.blacklivesmatter.policebrutality.config.PREF_KEY_LAST_UPDATED_TIMESTAMP_EPOCH_SECONDS
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.LocationIncidents
import com.blacklivesmatter.policebrutality.test.BaseTest
import com.blacklivesmatter.policebrutality.test.FakeSharedPreferences
import com.blacklivesmatter.policebrutality.test.MockLifecycleOwner
import com.blacklivesmatter.policebrutality.test.mock
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationViewModel.NavigationEvent
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationViewModel.RefreshEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

@ExperimentalCoroutinesApi
class LocationViewModelTest : BaseTest() {

    private val incidentRepository: IncidentRepository = mock()
    private val analytics: Analytics = mock()
    private val lifecycleOwner = MockLifecycleOwner()
    private val refreshEventObserver: Observer<RefreshEvent> = mock()
    private val navigationEventObserver: Observer<NavigationEvent> = mock()
    private val preferences: SharedPreferences = FakeSharedPreferences().preferences

    private lateinit var sut: LocationViewModel

    @Before
    fun setUp() {
        sut = LocationViewModel(incidentRepository, analytics, preferences)
    }

    @Test
    fun `onRefreshIncidentsRequested - given refresh already in progress - does not request refresh again`() {
        sut.isOperationInProgress.set(true)

        sut.refreshEvent.observe(lifecycleOwner, refreshEventObserver)

        sut.onRefreshIncidentsRequested()
        lifecycleOwner.start() // Start the lifecycle event after observing live data

        verify(refreshEventObserver, times(0)).onChanged(any())
    }

    @Test
    fun `onRefreshIncidentsRequested - given not refreshing - requests new incidents data`() = runBlockingTest {
        `when`(incidentRepository.getIncidentsCoroutine()).thenReturn(emptyList())

        sut.isOperationInProgress.set(false)

        sut.onRefreshIncidentsRequested()

        verify(incidentRepository, times(1)).getIncidentsCoroutine()
    }

    @Test
    fun `shouldRequestLatestData - given data is fresh - does not requests latest incidents`() {
        val timestampNow = LocalDateTime.now().minusDays(1L).toEpochSecond(ZoneOffset.UTC)
        preferences.edit(true) {
            putLong(PREF_KEY_LAST_UPDATED_TIMESTAMP_EPOCH_SECONDS, timestampNow)
        }

        assertFalse(sut.shouldRequestLatestData())
    }

    @Test
    fun `shouldRequestLatestData - given data is stale - requests for latest incidents`() {
        val staleTimestamp = LocalDateTime.now()
            .minusDays(INCIDENT_DATA_AUTO_REFRESH_DAYS.toLong() + 2) // Plus extra days
            .toEpochSecond(ZoneOffset.UTC)
        preferences.edit(true) {
            putLong(PREF_KEY_LAST_UPDATED_TIMESTAMP_EPOCH_SECONDS, staleTimestamp)
        }

        assertTrue(sut.shouldRequestLatestData())
    }

    @Test
    fun `onShowLatestIncidentsSelected - should navigate to latest incidents`() {
        sut.navigationEvent.observe(lifecycleOwner, navigationEventObserver)

        sut.onShowLatestIncidentsSelected()
        lifecycleOwner.start() // Start the lifecycle event after observing live data

        verify(navigationEventObserver, times(1)).onChanged(any())
    }

    @Test
    fun `onShowLatestIncidentsSelected - reports analytics action`() {
        sut.onShowLatestIncidentsSelected()

        verify(analytics).logEvent(Analytics.ACTION_INCIDENT_FILTER_RECENT)
    }

    @Test
    fun `onIncidentLocationSelected - should navigate to selected state`() {
        sut.navigationEvent.observe(lifecycleOwner, navigationEventObserver)


        val locationIncident = LocationIncidents(stateName = "StateName", totalIncidents = 1, lastReportedDate = null)
        sut.onIncidentLocationSelected(locationIncident)
        lifecycleOwner.start() // Start the lifecycle event after observing live data

        val captor = ArgumentCaptor.forClass(NavigationEvent::class.java)
        verify(navigationEventObserver, times(1)).onChanged(captor.capture())

        assertTrue(captor.value is NavigationEvent.Location)
        assertEquals(locationIncident.stateName, (captor.value as NavigationEvent.Location).stateName)
    }
}
