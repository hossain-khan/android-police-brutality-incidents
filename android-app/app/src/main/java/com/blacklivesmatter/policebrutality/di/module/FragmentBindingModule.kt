package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.ui.dashboard.DashboardFragment
import com.blacklivesmatter.policebrutality.ui.home.HomeFragment
import com.blacklivesmatter.policebrutality.ui.incident.IncidentsFragment
import com.blacklivesmatter.policebrutality.ui.notifications.NotificationsFragment
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