package com.example.myportfolio.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class, AdapterModule::class, AppSubcomponents::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
    fun newPortfolioComponent(): NewPortfolioComponent.Factory
    fun updatePortfolioComponent(): UpdatePortfolioComponent.Factory
    fun adapterSubcomponent(): AdapterSubcomponent.Factory
}