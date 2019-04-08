package com.levelup.currencyexchange.di

import com.levelup.currencyexchange.repository.datastore.RateRepos
import com.levelup.currencyexchange.repository.usecase.RateUsecase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UsecaseModule  {

    @Singleton
    @Provides
    internal fun providesRateUsecase(rateRepos: RateRepos): RateUsecase {
        return RateUsecase(rateRepos)
    }
}