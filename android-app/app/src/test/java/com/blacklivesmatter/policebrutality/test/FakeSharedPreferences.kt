package com.blacklivesmatter.policebrutality.test

import android.content.SharedPreferences
import org.mockito.Mockito
import org.mockito.stubbing.Answer

/**
 * A map-backed mock of SharedPreferences to avoid relying on Robolectric.
 *
 * Usage:
 * ```
 * private val preferences = MockSharedPreferences().preferences
 *
 * @Before
 * fun setUp() {
 *    sut = YourTestClass(preferences)
 * }
 * ```
 */
class FakeSharedPreferences {

    val preferences: SharedPreferences

    init {
        val values = mutableMapOf<String, Any?>()
        val editor = Mockito.mock(SharedPreferences.Editor::class.java, Answer answer@{
            if (it.method.name.startsWith("put")) {
                values[it.arguments[0] as String] = it.arguments[1]
            }
            return@answer if (it.method.returnType == SharedPreferences.Editor::class.java) {
                it.mock
            } else {
                null
            }
        })
        preferences = Mockito.mock(SharedPreferences::class.java, Answer answer@{
            return@answer if (it.method.name.startsWith("get")) {
                values[it.arguments[0]] ?: it.arguments[1]
            } else if (it.method.name == "edit") {
                editor
            } else {
                null
            }
        })
    }
}
