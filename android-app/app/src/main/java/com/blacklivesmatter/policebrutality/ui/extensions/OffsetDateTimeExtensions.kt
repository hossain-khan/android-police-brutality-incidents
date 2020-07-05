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

import com.blacklivesmatter.policebrutality.config.THE_846_DAY
import com.blacklivesmatter.policebrutality.config.TODAY
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

private const val unknownDateText = "Unknown Date"

/**
 * Extension to convert [OffsetDateTime] to human readable code.
 */
fun OffsetDateTime?.toDateText() = this?.let { incidentDate ->
    if (incidentDate.isBefore(THE_846_DAY)) {
        return@let unknownDateText
    } else {
        return@let incidentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
    }
} ?: unknownDateText

fun OffsetDateTime?.daysSinceToday(): Int {
    if (this == null) {
        return 0
    }

    return Duration.between(this, TODAY).toDays().toInt()
}
