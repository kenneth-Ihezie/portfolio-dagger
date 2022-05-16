package com.example.myportfolio.di

import com.example.myportfolio.view.NewPortfolio
import dagger.Subcomponent

@Subcomponent
interface NewPortfolioComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): NewPortfolioComponent
    }
    fun inject(activity: NewPortfolio)
}