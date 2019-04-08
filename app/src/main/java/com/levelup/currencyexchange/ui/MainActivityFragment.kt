package com.levelup.currencyexchange.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.irozon.sneaker.Sneaker
import com.levelup.currencyexchange.R
import com.levelup.currencyexchange.adapter.Recyler_currencyAdapter
import com.levelup.currencyexchange.repository.model.Rate
import com.levelup.currencyexchange.listener.OnAmountChangedListener
import com.levelup.currencyexchange.presenter.CurrencyXchngPresenter
import com.levelup.currencyexchange.view.XchngView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() , XchngView {

    @Inject
    lateinit var currencyXchngPresenter : CurrencyXchngPresenter

    private lateinit var adapter: Recyler_currencyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        currencyXchngPresenter.onActivityCreated()
        adapter = Recyler_currencyAdapter(object : OnAmountChangedListener {
            override fun onAmountChanged(symbol: String, amount: Float) {
                currencyXchngPresenter.CheckXhngRates(symbol,amount)
            }
        })

        initView()
    }

    private fun initView() {
        ryclcurrlist.setHasFixedSize(true)
        ryclcurrlist.layoutManager = LinearLayoutManager(context)
        ryclcurrlist.adapter = adapter

        ryclcurrlist.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideKeyboard()
            }
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        currencyXchngPresenter.xchngView = this
    }

    override fun updateRateList(rates: ArrayList<Rate>) {
        adapter.updateCurrencyRates(rates)
    }

    override fun updateAmount(amount: Float) {
        adapter.updatexchngamount(amount)
    }

    override fun showLoading(isLoading: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

        ryclcurrlist.visibility = if (isLoading) View.GONE else View.VISIBLE
        ryclcurrlist.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (isLoading) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                ryclcurrlist.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        })

        progressView.visibility = if (isLoading) View.VISIBLE else View.GONE
        progressView.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (isLoading) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                progressView.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        })
    }


    override fun showError() {
        Sneaker.with(activity)
            .setHeight(90)
            .setTitle(getString(R.string.error_unknown_title))
            .setMessage(getString(R.string.error_unknown))
            .sneakError()
    }

    override fun onDestroy() {
        super.onDestroy()
        currencyXchngPresenter.onDestroy()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
