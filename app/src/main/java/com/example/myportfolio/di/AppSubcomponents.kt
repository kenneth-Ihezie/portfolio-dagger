package com.example.myportfolio.di

import dagger.Module

@Module(subcomponents = [NewPortfolioComponent::class, UpdatePortfolioComponent::class, AdapterSubcomponent::class])
class AppSubcomponents