package com.blacklivesmatter.policebrutality.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}
