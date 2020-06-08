package com.github.policebrutality.di.module

import com.github.policebrutality.ui.dashboard.DashboardFragment
import com.github.policebrutality.ui.home.HomeFragment
import com.github.policebrutality.ui.incident.IncidentsFragment
import com.github.policebrutality.ui.notifications.NotificationsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeIncidentsFragment(): IncidentsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): NotificationsFragment
}