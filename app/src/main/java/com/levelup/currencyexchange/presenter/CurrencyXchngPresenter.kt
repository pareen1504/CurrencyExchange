package com.levelup.currencyexchange.presenter

import com.levelup.currencyexchange.repository.model.Rate
import com.levelup.currencyexchange.repository.usecase.RateUsecase
import com.levelup.currencyexchange.view.XchngView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyXchngPresenter @Inject constructor(private val ratesUsecase: RateUsecase) {

    companion object {
        const val DEFAULT_SYMBOL = "EUR"
    }

    lateinit var xchngView: XchngView

    private var currentBase: String = ""
    private var viewStopped = false
    private var isLoadingComplete = false

    fun CheckXhngRates(base: String, amount: Float) {
        if (base.equals(currentBase, ignoreCase = true)) {
            xchngView.updateAmount(amount)
        } else {
            currentBase = base.toUpperCase()
            ratesUsecase.getRates(base)
                .doOnSubscribe {
                    if (!isLoadingComplete) {
                        xchngView.showLoading(true)
                    }
                }
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatUntil({ viewStopped || !base.equals(currentBase, ignoreCase = true) })
                .subscribe({
                    val rates = ArrayList<Rate>()
                    rates.add(Rate(it.base, 1.0F))
                    rates.addAll(it.rates.map { Rate(it.key, it.value) })

                    xchngView.updateRateList(rates)
                    if (!isLoadingComplete) {
                        xchngView.showLoading(false)
                    }

                    isLoadingComplete = true
                }, { xchngView.showError() })

        }
    }

    fun onActivityCreated(){
        CheckXhngRates(DEFAULT_SYMBOL,1F)
    }

    fun onDestroy(){
        viewStopped=true
    }
}