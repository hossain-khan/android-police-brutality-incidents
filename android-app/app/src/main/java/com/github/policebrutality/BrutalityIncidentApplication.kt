package com.github.policebrutality

import com.github.policebrutality.di.component.AppComponent
import com.github.policebrutality.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import kotlin.math.abs

class BrutalityIncidentApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        // Build app component
        val appComponent: AppComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        // Inject the application instance
        appComponent.inject(this)
        return appComponent
    }



    private fun installLogging() {
        // Include logging in release builds so we can test from client.
        // See https://github.com/JakeWharton/timber/blob/master/timber-sample/src/main/java/com/example/timber/ExampleApp.java
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                val newMessage = "[${getThreadName()}] $message"
                super.log(priority, tag, newMessage, t)
            }

            /**
             * Returns name of current thread, with some well known thread names replaced to make easier to understand.
             */
            private fun getThreadName(): String {
                var name = Thread.currentThread().name
                if (name.length > 10) {
                    name = name.substring(0, 10) + "." + abs(name.hashCode() % 100)
                }
                return name
            }
        })
    }
}