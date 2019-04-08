package com.levelup.currencyexchange.repository.network

import com.levelup.currencyexchange.repository.model.ListRate
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface RateService {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org/"
    }

    @GET("/latest")
    fun getNewRates(@Query("base") base:String):Single<ListRate>
}