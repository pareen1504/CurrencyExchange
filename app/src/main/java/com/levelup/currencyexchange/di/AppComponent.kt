package com.levelup.currencyexchange.di

import android.app.Application
import android.content.Context
import com.levelup.currencyexchange.application.XchngApplication
import com.levelup.currencyexchange.repository.datastore.ReposModule
import com.levelup.currencyexchange.repository.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivityModule::class, FragmentModule::class, NetworkModule::class, ReposModule::class])
interface AppComponent {

    fun inject(application: XchngApplication)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun cotnext(context:Context):Builder

        fun build(): AppComponent
    }
}