package com.example.myportfolio.view

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myportfolio.R
import com.example.myportfolio.model.Portfolio
import kotlinx.android.synthetic.main.recycler_portfolio.view.*
import javax.inject.Inject

class PortfolioAdapter @Inject constructor(context: Context, private val listener: PortfolioAdapterListener) : RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var portfolios = emptyList<Portfolio>()

    innerfal class PortfolioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        val portfolioView: TextView = itemView.itemPortfolio
        val portfolioCostView: TextView = itemView.costPortfolio
        val portfolioTimeView: TextView = itemView.timePortfolio
        val portfolioDateView: TextView = itemView.datePortfolio

        init {
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(view: View): Boolean {
            listener.onRowLongClicked(adapterPosition)
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_portfolio, parent, false)
        return PortfolioViewHolder(itemView)
    }

    override fun getItemCount() = portfolios.size

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val current = portfolios[position]
        holder.portfolioView.text = current.portfolioItem
        holder.portfolioCostView.text = current.portfolioCost
        holder.portfolioTimeView.text = current.portfolioTime
        holder.portfolioDateView.text = current.portfolioDate
    }



    internal fun setPortfolio(portfolio: List<Portfolio>){
        this.portfolios = portfolio
        notifyDataSetChanged()
    }

    interface PortfolioAdapterListener {
        fun onRowLongClicked(position: Int)
    }

    fun getPortfolioAtPosition(position: Int): Portfolio{
        return portfolios[position]
    }
}
