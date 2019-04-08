package com.levelup.currencyexchange.repository.model

data class ListRate(val base: String, val date: String, val rates: Map<String, Float>)