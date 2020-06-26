
package com.blacklivesmatter.policebrutality.test

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.net.ConnectivityManager
import java.io.ByteArrayInputStream
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

/*
 * Functions and extensions for Mockito.
 */

fun <T> anyKObject(): T {
    Mockito.any<T>()
    @Suppress("UNCHECKED_CAST")
    return null as T
}

fun <T> anyKObject(clazz: Class<T>): T {
    Mockito.any<T>(clazz)
    @Suppress("UNCHECKED_CAST")
    return null as T
}

fun Context.addConnectivityService(): ConnectivityManager {
    Mockito.`when`(this.applicationContext).thenReturn(this)
    val connectivityManager = Mockito.mock(ConnectivityManager::class.java)
    Mockito.`when`(this.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
    return connectivityManager
}

fun createMockContext(): Application {
    val resources = Mockito.mock(Resources::class.java)
    Mockito.`when`(resources.getString(Mockito.anyInt())).thenReturn("Wonderful")

    val context = Mockito.mock(Application::class.java)
    Mockito.`when`(context.resources).thenReturn(resources)
    Mockito.`when`(context.getString(Mockito.anyInt())).thenAnswer { invocation ->
        resources.getString(invocation.arguments[0] as Int)
    }

    val assets = Mockito.mock(AssetManager::class.java)
    Mockito.`when`(assets.open(anyKObject())).thenReturn(ByteArrayInputStream(byteArrayOf()))
    Mockito.`when`(context.assets).thenReturn(assets)

    // Add this automically, however if the caller needs access they should call [addConnectivityService] themselves.
    context.addConnectivityService()

    return context
}

inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)

/**
 * Use this function instead of using [ArgumentCaptor.capture] directly.
 * The Mockito API will not work in Kotlin since it returns a nullable type.
 * This function is a workaround for that problem.
 *
 * See: https://stackoverflow.com/questions/34773958/kotlin-and-argumentcaptor-illegalstateexception
 **/
fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

/**
 * Similar to the above, the [Mockito.argThat] method doesn't work when used directly due to
 * it returning a nullable type
 **/
fun <T> argThat(matcher: (T) -> Boolean): T = Mockito.argThat(matcher)
