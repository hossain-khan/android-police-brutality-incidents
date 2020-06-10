package com.blacklivesmatter.policebrutality.di.module

import com.blacklivesmatter.policebrutality.worker.SeedDatabaseWorker
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Module to bind Work Manager workers.
 *
 * See [Inject into Workers (androidx.WorkManager API)](https://github.com/google/dagger/issues/1183#issuecomment-601158396)
 */
@Module(includes = [AndroidInjectionModule::class])
abstract class WorkerModule {
    @ContributesAndroidInjector
    abstract fun bindSeedDatabaseWorker(): SeedDatabaseWorker
}
