package com.levelup.currencyexchange.di

import com.levelup.currencyexchange.ui.MainActivityFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule{

    @ContributesAndroidInjector
    internal abstract fun xchngFragment(): MainActivityFragment

}
