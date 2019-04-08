package com.levelup.currencyexchange.repository.datastore

import com.levelup.currencyexchange.repository.model.ListRate
import com.levelup.currencyexchange.repository.network.RateService
import io.reactivex.Single
import javax.inject.Inject

class RateNetworkData @Inject constructor(private val rateService: RateService) : RateDatastore{
    override fun getRates(base: String): Single<ListRate> {
        return rateService.getNewRates(base)
    }
}