package com.example.myportfolio

import android.app.Application
import com.example.myportfolio.di.ApplicationComponent
import com.example.myportfolio.di.DaggerApplicationComponent

class MyApplication : Application(){
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}