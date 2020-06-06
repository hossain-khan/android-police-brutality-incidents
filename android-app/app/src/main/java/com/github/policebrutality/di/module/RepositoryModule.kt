package com.github.policebrutality.di.module

import com.github.policebrutality.data.BrutalityIncidentRepository
import com.github.policebrutality.data.IncidentRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsIncidentRepository(repository: BrutalityIncidentRepository): IncidentRepository
}