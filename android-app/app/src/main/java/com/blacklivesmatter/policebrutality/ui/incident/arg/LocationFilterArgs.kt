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

package com.blacklivesmatter.policebrutality.ui.incident.arg

import android.os.Parcelable
import androidx.annotation.Keep
import com.blacklivesmatter.policebrutality.ui.incident.IncidentsFragment
import kotlinx.android.parcel.Parcelize

/**
 * Navigation args for [IncidentsFragment] to show different kind of filtered incidents.
 * 1. [FilterType.STATE] - where `[stateName]` required and provided.
 * 2. [FilterType.DATE] - where both `[timestamp]` and `[dateText]` are required and provided.
 * 3. [FilterType.LATEST] - No data required, shows most recent incident first.
 */
@Parcelize
@Keep
data class LocationFilterArgs(
    val type: FilterType,
    val stateName: String? = null,
    val timestamp: Long? = null,
    val dateText: String? = null
) : Parcelable
