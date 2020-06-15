package com.blacklivesmatter.policebrutality

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.blacklivesmatter.policebrutality.logging.TimberDebugTree
import com.blacklivesmatter.policebrutality.logging.TimberReleaseTree
import com.blacklivesmatter.policebrutality.ui.common.ThemeHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BrutalityIncidentApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        // Sets the application theme to dark-mode regardless of user's preferences
        // Because it's #BlackLivesMatter
        ThemeHelper.applyTheme(AppCompatDelegate.MODE_NIGHT_YES)

        installLogging()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    private fun installLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        } else {
            Timber.plant(TimberReleaseTree())
        }
    }
}
