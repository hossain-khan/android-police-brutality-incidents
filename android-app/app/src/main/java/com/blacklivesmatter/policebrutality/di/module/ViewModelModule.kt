package com.blacklivesmatter.policebrutality.di.module

import androidx.lifecycle.ViewModel
import com.blacklivesmatter.policebrutality.MainViewModel
import com.blacklivesmatter.policebrutality.di.annotation.ViewModelKey
import com.blacklivesmatter.policebrutality.ui.incident.IncidentViewModel
import com.blacklivesmatter.policebrutality.ui.incidentlocations.LocationViewModel
import com.blacklivesmatter.policebrutality.ui.moreinfo.MoreInfoViewModel
import com.blacklivesmatter.policebrutality.ui.newreport.NewReportViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewReportViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: NewReportViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun bindHomeViewModel(viewModel: LocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncidentViewModel::class)
    abstract fun bindIncidentViewModel(viewModel: IncidentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreInfoViewModel::class)
    abstract fun bindNotificationsViewModel(viewModel: MoreInfoViewModel): ViewModel
}
