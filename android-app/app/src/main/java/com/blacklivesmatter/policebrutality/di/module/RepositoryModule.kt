package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.data.BrutalityIncidentRepository
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsIncidentRepository(repository: BrutalityIncidentRepository): IncidentRepository
}
