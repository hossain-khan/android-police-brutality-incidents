package com.github.policebrutality.di.component

import android.app.Application
import com.github.policebrutality.BrutalityIncidentApplication
import com.github.policebrutality.di.module.ActivityModule
import com.github.policebrutality.di.module.ApiModule
import com.github.policebrutality.di.module.AppModule
import com.github.policebrutality.di.module.DaoModule
import com.github.policebrutality.di.module.RepositoryModule
import com.github.policebrutality.di.module.ViewModelModule
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