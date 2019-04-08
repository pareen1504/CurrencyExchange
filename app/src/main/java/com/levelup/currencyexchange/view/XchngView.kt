package com.levelup.currencyexchange.view

import com.levelup.currencyexchange.repository.model.Rate

interface XchngView {
    fun updateRateList(rates: ArrayList<Rate>)
    fun updateAmount(amount: Float)
    fun showLoading(isLoading: Boolean)
    fun showError()
}
