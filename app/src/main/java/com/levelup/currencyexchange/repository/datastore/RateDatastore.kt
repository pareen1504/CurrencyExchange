package com.levelup.currencyexchange.repository.datastore

import com.levelup.currencyexchange.repository.model.ListRate
import io.reactivex.Single

interface RateDatastore {
    fun getRates(base:String):Single<ListRate>
}