package com.github.policebrutality.di.module

import androidx.lifecycle.ViewModel
import com.github.policebrutality.MainViewModel
import com.github.policebrutality.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel
}