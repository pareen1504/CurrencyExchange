package com.levelup.currencyexchange.repository.usecase

import com.levelup.currencyexchange.repository.datastore.RateRepos
import com.levelup.currencyexchange.repository.model.ListRate
import io.reactivex.Single
import javax.inject.Inject

class RateUsecase @Inject constructor(private val rateRepos: RateRepos)  {
    fun getRates(base: String): Single<ListRate> {
        return rateRepos.getRates(base)
    }
}