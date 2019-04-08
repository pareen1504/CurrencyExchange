package com.levelup.currencyexchange.listener

interface OnAmountChangedListener {
    fun onAmountChanged(symbol: String, amount: Float)

}