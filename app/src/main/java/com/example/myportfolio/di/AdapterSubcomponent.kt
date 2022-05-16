package com.example.myportfolio.di

import android.content.Context
import com.example.myportfolio.view.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface AdapterSubcomponent{
    @Subcomponent.Factory
    interface Factory {
        fun create(): AdapterSubcomponent
    }
    fun inject(activity: MainActivity)
}