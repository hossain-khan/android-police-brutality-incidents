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

package com.blacklivesmatter.policebrutality.data

import com.blacklivesmatter.policebrutality.config.THE_846_DAY
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ConvertersTest {

    private lateinit var sut: Converters

    private val jsonArray = "[\"Link-1\",\"Link-2\"]"
    private val items = listOf("Link-1", "Link-2")
    private val the846DateText: String = "2020-05-25T08:19:00-05:00"

    @Before
    fun setUp() {
        sut = Converters()
    }

    @Test
    fun listOfLinksToString() {
        val listOfLinksToString = sut.listOfLinksToString(items)

        assertEquals(jsonArray, listOfLinksToString)
    }

    @Test
    fun linksJsonToLinksArray() {
        val linksJsonToLinksArray = sut.linksJsonToLinksArray(jsonArray)

        assertEquals(items, linksJsonToLinksArray)
    }

    @Test
    fun toOffsetDateTime() {
        val toOffsetDateTime = sut.toOffsetDateTime(the846DateText)

        assertEquals(THE_846_DAY, toOffsetDateTime)
    }

    @Test
    fun `toOffsetDateTime - given null - returns null`() {
        val toOffsetDateTime = sut.toOffsetDateTime(null)

        assertNull(toOffsetDateTime)
    }

    @Test
    fun fromOffsetDateTime() {
        val fromOffsetDateTime = sut.fromOffsetDateTime(THE_846_DAY)
        assertEquals(the846DateText, fromOffsetDateTime)
    }

    @Test
    fun `fromOffsetDateTime - given null - returns null`() {
        val fromOffsetDateTime = sut.fromOffsetDateTime(null)

        assertNull(fromOffsetDateTime)
    }
}
