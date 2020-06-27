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

package com.blacklivesmatter.policebrutality.ui.incident

import androidx.lifecycle.Observer
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.test.BaseTest
import com.blacklivesmatter.policebrutality.test.FakeSharedPreferences
import com.blacklivesmatter.policebrutality.test.MockLifecycleOwner
import com.blacklivesmatter.policebrutality.test.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class IncidentViewModelTest : BaseTest() {

    private lateinit var sut: IncidentViewModel
    private val incidentRepository: IncidentRepository = mock()
    private val preferences = FakeSharedPreferences().preferences
    private val lifecycleOwner = MockLifecycleOwner()
    private val liveDataObserver: Observer<Incident> = mock()

    @Before
    fun setUp() {
        sut = IncidentViewModel(incidentRepository, preferences)
    }

    @Test
    fun `onShareIncidentClicked - given incident selected - emits selected incident`() {
        val incident = Incident(
            id = "123",
            incident_id = "ny-123",
            state = "NY",
            city = "NYC",
            name = "Name",
            date = null,
            geocoding = null,
            links = emptyList()
        )

        sut.shareIncident.observe(lifecycleOwner, liveDataObserver)
        sut.onShareIncidentClicked(incident)

        lifecycleOwner.start()

        verify(liveDataObserver, times(1)).onChanged(incident)
    }
}
