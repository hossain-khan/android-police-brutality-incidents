package com.blacklivesmatter.policebrutality.di

import android.content.Context
import com.blacklivesmatter.policebrutality.analytics.Analytics
import com.blacklivesmatter.policebrutality.analytics.AppAnalytics
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AnalyticsModule {
    @Singleton
    @Provides
    fun provideAnalytics(@ApplicationContext appContext: Context): FirebaseAnalytics {
        // https://firebase.google.com/docs/analytics/get-started?platform=android
        val instance = FirebaseAnalytics.getInstance(appContext)
        Timber.d("Providing firebase analytics instance: $instance")
        return instance
    }

    @Singleton
    @Provides
    fun provideAppAnalytics(firebaseAnalytics: FirebaseAnalytics): Analytics {
        return AppAnalytics(firebaseAnalytics)
    }
}
