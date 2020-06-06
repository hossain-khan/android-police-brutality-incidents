package com.github.policebrutality

import com.github.policebrutality.di.component.AppComponent
import com.github.policebrutality.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

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
}