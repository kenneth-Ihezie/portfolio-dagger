package com.example.myportfolio.di

import android.content.Context
import com.example.myportfolio.view.PortfolioAdapter
import javax.inject.Inject

class AdapterPreference @Inject constructor() : PortfolioAdapter.PortfolioAdapterListener {
    override fun onRowLongClicked(position: Int) {

    }
}
