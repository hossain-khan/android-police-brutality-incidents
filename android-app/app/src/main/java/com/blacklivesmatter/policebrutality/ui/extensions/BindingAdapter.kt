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

package com.blacklivesmatter.policebrutality.ui.extensions

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.size.Scale
import com.blacklivesmatter.policebrutality.R
import com.blacklivesmatter.policebrutality.data.model.Incident
import com.google.android.material.textview.MaterialTextView

/**
 * Loads the image view wil provided url.
 */
@BindingAdapter("imageUrl")
fun AppCompatImageView.loadImage(imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) {
        // do nothing
    } else {
        this.load(imageUrl) {
            crossfade(true)
            scale(Scale.FIT)
        }
    }
}


@BindingAdapter("mapIconIfAvailable")
fun MaterialTextView.showMapIconIfLocationAvailable(incident: Incident) {
    if (incident.hasValidGeocodingData) {
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(
            /* start */ 0,
            /* top */ 0,
            /* end */ R.drawable.ic_outline_pin_drop,
            /* bottom */ 0
        )
    }
}
