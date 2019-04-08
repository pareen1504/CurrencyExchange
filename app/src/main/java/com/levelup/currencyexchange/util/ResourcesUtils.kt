package com.levelup.currencyexchange.util

import android.content.Context

fun getCurrencyNameResId(context: Context, symbol: String) =
        context.resources.getIdentifier("currency_" + symbol + "_name", "string",
                                        context.packageName)

fun getCurrencyFlagResId(context: Context, symbol: String) = context.resources.getIdentifier(
        "ic_" + symbol + "_flag", "drawable", context.packageName)