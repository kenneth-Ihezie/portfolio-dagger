package com.example.myportfolio.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myportfolio.MyApplication
import com.example.myportfolio.model.Portfolio
import com.example.myportfolio.model.PortfolioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class PortfolioViewModel @Inject constructor(private var repository: PortfolioRepository, context: Context) : AndroidViewModel(
    context as MyApplication
) {
   val allPortfolios: LiveData<List<Portfolio>> = repository.allPortfolio


    fun insertPortfolio(portfolio: Portfolio)= viewModelScope.launch(Dispatchers.IO){
       repository.insertPortfolio(portfolio)

       }

    fun deletePortfolio(portfolio: Portfolio) = viewModelScope.launch(Dispatchers.IO){
        repository.deletePortfolio(portfolio)
    }

     fun updatePortfolio(portfolioId: Long, portfolioItem: String, portfolioCost: String, portfolioDate: String, portfolioTime: String) = viewModelScope.launch(Dispatchers.IO){
        repository.updatePortfolio(portfolioId, portfolioItem, portfolioCost, portfolioTime, portfolioDate)
    }
 }