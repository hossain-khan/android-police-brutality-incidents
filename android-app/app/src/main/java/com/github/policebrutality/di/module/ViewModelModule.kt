package com.github.policebrutality.di.module

import androidx.lifecycle.ViewModel
import com.github.policebrutality.MainViewModel
import com.github.policebrutality.di.annotation.ViewModelKey
import com.github.policebrutality.ui.dashboard.DashboardViewModel
import com.github.policebrutality.ui.home.HomeViewModel
import com.github.policebrutality.ui.incident.IncidentViewModel
import com.github.policebrutality.ui.notifications.NotificationsViewModel
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
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncidentViewModel::class)
    abstract fun bindIncidentViewModel(viewModel: IncidentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationsViewModel::class)
    abstract fun bindNotificationsViewModel(viewModel: NotificationsViewModel): ViewModel
}