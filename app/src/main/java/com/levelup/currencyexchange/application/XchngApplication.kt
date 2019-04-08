package com.levelup.currencyexchange.application

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.levelup.currencyexchange.di.DaggerAppComponent

import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class XchngApplication : Application(), HasActivityInjector,HasSupportFragmentInjector {

    @Inject
    lateinit var activityinjection: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentinjection: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> = activityinjection

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentinjection

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .cotnext(this)
            .build()
            .inject(this)
    }
}