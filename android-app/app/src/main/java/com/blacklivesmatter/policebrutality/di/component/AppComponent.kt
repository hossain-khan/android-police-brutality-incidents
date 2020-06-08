package com.blacklivesmatter.policebrutality.di.component

import android.app.Application
import com.blacklivesmatter.policebrutality.BrutalityIncidentApplication
import com.blacklivesmatter.policebrutality.di.module.ActivityModule
import com.blacklivesmatter.policebrutality.di.module.ApiModule
import com.blacklivesmatter.policebrutality.di.module.AppModule
import com.blacklivesmatter.policebrutality.di.module.DaoModule
import com.blacklivesmatter.policebrutality.di.module.RepositoryModule
import com.blacklivesmatter.policebrutality.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        DaoModule::class,
        ApiModule::class
    ]
)
interface AppComponent : AndroidInjector<BrutalityIncidentApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: BrutalityIncidentApplication?)
}