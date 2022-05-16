package com.example.myportfolio.di

import com.example.myportfolio.view.PortfolioAdapter
import dagger.Binds
import dagger.Module

@Module
abstract class AdapterModule {
    @Binds
    abstract fun provideListener(listener: AdapterPreference): PortfolioAdapter.PortfolioAdapterListener
}