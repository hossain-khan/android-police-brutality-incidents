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

import androidx.lifecycle.Observer
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.test.BaseTest
import com.blacklivesmatter.policebrutality.test.MockLifecycleOwner
import com.blacklivesmatter.policebrutality.test.mock
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationViewModel.RefreshEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class LocationViewModelTest : BaseTest() {

    private val incidentRepository: IncidentRepository = mock()
    private val analytics: Analytics = mock()
    private val lifecycleOwner = MockLifecycleOwner()
    private val liveDataObserver: Observer<RefreshEvent> = mock()

    private lateinit var sut: LocationViewModel

    @Before
    fun setUp() {
        sut = LocationViewModel(incidentRepository, analytics)
    }

    @Test
    fun `onRefreshIncidentsRequested - given refresh already in progress - does not request refresh again`() {
        sut.isOperationInProgress.set(true)

        sut.refreshEvent.observe(lifecycleOwner, liveDataObserver)

        sut.onRefreshIncidentsRequested()
        lifecycleOwner.start() // Start the lifecycle event after observing live data

        verify(liveDataObserver, times(0)).onChanged(any())
    }

    @Test
    fun `onRefreshIncidentsRequested - given not refreshing - requests new incidents data`() = runBlockingTest {
        `when`(incidentRepository.getIncidentsCoroutine()).thenReturn(emptyList())

        sut.isOperationInProgress.set(false)

        sut.onRefreshIncidentsRequested()

        verify(incidentRepository, times(1)).getIncidentsCoroutine()
    }
}
