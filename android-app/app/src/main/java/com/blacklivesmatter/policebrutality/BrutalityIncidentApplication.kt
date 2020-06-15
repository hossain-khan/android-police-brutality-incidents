package com.blacklivesmatter.policebrutality

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.blacklivesmatter.policebrutality.ui.common.ThemeHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.abs

@HiltAndroidApp
class BrutalityIncidentApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        // Sets the application theme to dark-mode regardless of user's preferences
        // Because it's #BlackLivesMatter
        ThemeHelper.applyTheme(AppCompatDelegate.MODE_NIGHT_YES)

        if (BuildConfig.DEBUG) {
            installLogging()
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
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
