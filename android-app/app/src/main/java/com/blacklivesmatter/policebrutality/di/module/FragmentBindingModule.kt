package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.ui.newreport.NewReportFragment
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationFragment
import com.blacklivesmatter.policebrutality.ui.incident.IncidentsFragment
import com.blacklivesmatter.policebrutality.ui.moreinfo.MoreInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): NewReportFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): LocationFragment

    @ContributesAndroidInjector
    abstract fun contributeIncidentsFragment(): IncidentsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): MoreInfoFragment
}