package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.MainActivity
import com.blacklivesmatter.policebrutality.ui.launcher.LauncherActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class, FragmentBindingModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindLauncherActivity(): LauncherActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
