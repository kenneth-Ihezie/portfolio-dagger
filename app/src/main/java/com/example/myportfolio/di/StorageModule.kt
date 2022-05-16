package com.example.myportfolio.di

import android.content.Context
import com.example.myportfolio.model.MyDatabase
import com.example.myportfolio.model.PortfolioDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    //we called the class StorageModule to group
    // the logic of providing objects related to storage.
    // If our application expands, we could also include
    // how to provide different implementations of SharedPreferences
    @Singleton
    @Provides
    fun provideDatabase(context: Context): MyDatabase = MyDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providePortfolioDao(db: MyDatabase): PortfolioDao = db.portfolioDao()

  /*  @Singleton
    @Provides
    fun provideListener(listener: AdapterListener): PortfolioAdapter.PortfolioAdapterListener = listener.provideAdapterListener()*/

}