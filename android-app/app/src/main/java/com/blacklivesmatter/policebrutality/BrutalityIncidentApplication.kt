package com.blacklivesmatter.policebrutality

import androidx.appcompat.app.AppCompatDelegate
import com.blacklivesmatter.policebrutality.di.component.AppComponent
import com.blacklivesmatter.policebrutality.di.component.DaggerAppComponent
import com.blacklivesmatter.policebrutality.ui.common.ThemeHelper
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlin.math.abs
import timber.log.Timber

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

    override fun onCreate() {
        super.onCreate()

        // Sets the application theme to dark-mode regardless of user's preferences
        // Because it's #BlackLivesMatter
        ThemeHelper.applyTheme(AppCompatDelegate.MODE_NIGHT_YES)

        if (BuildConfig.DEBUG) {
            installLogging()
        }
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
