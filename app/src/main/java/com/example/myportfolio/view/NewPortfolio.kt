package com.example.myportfolio.view

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myportfolio.MyApplication
import com.example.myportfolio.R
import com.example.myportfolio.di.NewPortfolioComponent
import com.example.myportfolio.di.NewPortfolioScope
import com.example.myportfolio.model.Portfolio
import com.example.myportfolio.viewmodel.PortfolioViewModel
import kotlinx.android.synthetic.main.activity_portfolio.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class NewPortfolio : AppCompatActivity() {
    @Inject
    lateinit var portfolioViewModel: PortfolioViewModel

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
    (application as MyApplication).applicationComponent.newPortfolioComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

        portfolioViewModel.allPortfolios.observe(this, Observer { portfolios ->
            Log.d("key", "print")

            for (portfolio in portfolios) {
                Log.d("PORTFOLIO", "${portfolio.portfolioId}")
            }
        })
        timePort.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                val timeSet = SimpleDateFormat("HH:mm").format(cal.time)
                val timePort = findViewById<TextView>(R.id.timePort)
                timePort.text = timeSet
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

          portfolio_add.setOnClickListener {
            if (TextUtils.isEmpty(itemPort.text) || TextUtils.isEmpty(costPort.text) || TextUtils.isEmpty(timePort.text) || TextUtils.isEmpty(datePortfolio.text)){
               Toast.makeText(this, "Portfolio field cant be empty", Toast.LENGTH_LONG).show()
            } else {
                val addPortfolio = Intent(this, MainActivity::class.java).apply {
                    val insert = Portfolio(itemPort.text.toString(), costPort.text.toString(), timePort.text.toString(), datePortfolio.text.toString())
                    portfolioViewModel.insertPortfolio(insert)
                }
                startActivity(addPortfolio)
            }
            finish()
        }

    }
    companion object{
        const val UID = "uid"
    }
}
