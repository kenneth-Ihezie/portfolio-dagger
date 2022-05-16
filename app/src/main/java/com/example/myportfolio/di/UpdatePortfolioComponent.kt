package com.example.myportfolio.di

import com.example.myportfolio.view.UpdateActivity
import dagger.Subcomponent


@Subcomponent
interface UpdatePortfolioComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): UpdatePortfolioComponent
    }
    fun inject(activity: UpdateActivity)
}