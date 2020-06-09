package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.data.BrutalityIncidentRepository
import com.blacklivesmatter.policebrutality.data.IncidentRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsIncidentRepository(repository: BrutalityIncidentRepository): IncidentRepository
}
