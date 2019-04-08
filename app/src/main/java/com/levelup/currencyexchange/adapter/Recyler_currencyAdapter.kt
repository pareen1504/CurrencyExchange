package com.levelup.currencyexchange.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.levelup.currencyexchange.R
import com.levelup.currencyexchange.repository.model.Rate
import com.levelup.currencyexchange.listener.OnAmountChangedListener
import com.levelup.currencyexchange.util.*
import kotlinx.android.synthetic.main.recyler_row_view.view.*
import kotlin.coroutines.coroutineContext

class Recyler_currencyAdapter(private val onAmountChangedListener: OnAmountChangedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var amount = 1.0F
    private var getcodeposition = ArrayList<String>()
    private var currencyrate = HashMap<String, Rate>()

    fun updateCurrencyRates(rates: ArrayList<Rate>) {
        if (getcodeposition.isEmpty()) {
            getcodeposition.addAll(rates.map { it.symbol })
        }

        for (rate in rates) {
            currencyrate[rate.symbol] = rate
        }

        notifyItemRangeChanged(0, getcodeposition.size - 1, amount)
    }

    fun updatexchngamount(amount: Float) {
        this.amount = amount
        notifyItemRangeChanged(0, getcodeposition.size - 1, amount)
    }

    private fun rateAtPosition(pos: Int): Rate {
        return currencyrate[getcodeposition[pos]]!!
    }

    private fun symbolAtPostion(pos: Int): String {
        return getcodeposition[pos]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyler_row_view, parent, false))
    }

    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var flag: ImageView = itemView.icCurrencyFlag
        var currencyCode: TextView = itemView.lblCurrencySymbol
        var currencyName: TextView = itemView.lblCurrencyName
        var xchngamount: EditText = itemView.txtCurrencyAmount
        var code: String = ""


        fun bind(rate: Rate) {
            if (code != rate.symbol) {
                initView(rate)
                this.code = rate.symbol
            }
            if (!xchngamount.isFocused) {
                if (amount.equals(1.0F)) {
                    xchngamount.setText((rate.rate * amount).format())
                }else{
                    xchngamount.setText((rate.rate * amount).greaterthanoneformat())
                }
            }
        }

        private fun initView(rate: Rate) {
            val symbol = rate.symbol.toLowerCase()
            val nameId = getCurrencyNameResId(itemView.context, symbol)
            val flagid = getCurrencyFlagResId(itemView.context, symbol)

            currencyCode.text = rate.symbol
            currencyName.text = itemView.context.getString(nameId)
            flag.setImageResource(flagid)

            xchngamount.onFocusChangeListener = View.OnFocusChangeListener { _, hasfocus ->
                if (!hasfocus) {
                    return@OnFocusChangeListener
                }

                layoutPosition.takeIf { it > 0 }?.also { currentposition ->
                    getcodeposition.removeAt(currentposition).also {
                        getcodeposition.add(0, it)
                    }
                    notifyItemMoved(currentposition, 0)
                }
            }

            xchngamount.setOnKeyListener(View.OnKeyListener { _, keycode, event ->
                if (keycode == KeyEvent.KEYCODE_ENTER) {
                    symbolAtPostion(0)
                    return@OnKeyListener true
                }
                false

            })

            xchngamount.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (xchngamount.isFocused &&!p0.toString().equals(".")) {
                        onAmountChangedListener.onAmountChanged(symbolAtPostion(0), p0.toString().toFloat())
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return getcodeposition.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (!payloads.isEmpty()) {
            (holder as RateViewHolder).bind(rateAtPosition(position))
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RateViewHolder).bind(rateAtPosition(position))
    }
}