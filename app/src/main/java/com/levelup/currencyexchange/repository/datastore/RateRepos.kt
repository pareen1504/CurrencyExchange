package com.levelup.currencyexchange.repository.datastore

import com.levelup.currencyexchange.repository.model.ListRate
import io.reactivex.Single
import javax.inject.Inject

class RateRepos @Inject constructor(private val rateDatastore: RateDatastore) {
    fun getRates(base: String): Single<ListRate> {
        return rateDatastore.getRates(base)
    }
}