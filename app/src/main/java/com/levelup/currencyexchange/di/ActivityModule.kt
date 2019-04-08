package com.levelup.currencyexchange.di

import com.levelup.currencyexchange.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal  abstract fun mainactivity(): MainActivity
}