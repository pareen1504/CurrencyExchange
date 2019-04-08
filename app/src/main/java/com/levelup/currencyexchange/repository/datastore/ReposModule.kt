package com.levelup.currencyexchange.repository.datastore

import com.levelup.currencyexchange.repository.network.RateService

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReposModule {

    @Singleton
    @Provides
    internal fun providesRateRestDatastore(rateService: RateService): RateNetworkData {
        return RateNetworkData(rateService)
    }

    @Singleton
    @Provides
    internal fun providesRateRepository(rateNetworkData: RateNetworkData): RateRepos {
        return RateRepos(rateNetworkData)
    }
}
