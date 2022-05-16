package com.example.myportfolio.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myportfolio.MyApplication
import com.example.myportfolio.R
import com.example.myportfolio.R.id
import com.example.myportfolio.model.Portfolio
import com.example.myportfolio.viewmodel.PortfolioViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), PortfolioAdapter.PortfolioAdapterListener{
    @Inject
    lateinit var portfolioViewModel: PortfolioViewModel

    @Inject
    lateinit var mAdapter: PortfolioAdapter


    private var actionMode: ActionMode? = null
    private lateinit var portfolioList: List<Portfolio>
    private lateinit var positionInt: String
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).applicationComponent.adapterSubcomponent().create().inject(this)
        Log.d("view", "$portfolioViewModel")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(id.recyclePortfolio)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        portfolioViewModel.allPortfolios.observe(this, Observer { portfolio ->
            portfolio?.let {
                portfolioList = it
                mAdapter.setPortfolio(it)
                val portfolioLength = portfolio.size
                portfolioSize.text = portfolioLength.toString()
                val arrayList = ArrayList<Int>()
                for (i in portfolio) {
                    arrayList.add(i.portfolioCost.toInt())
                }
                numberCost.text = arrayList.sum().toString()
                Log.d("KeyIndex", "${numberCost.text}")
                for (i in portfolioList){
                    Log.d("list", "$i")
                }
            }
        })
            val itemTouchHelperCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        myDeleteViewHolder(viewHolder)
                        Toast.makeText(this@MainActivity, "Deleting portfolio...", Toast.LENGTH_LONG).show()
                    }
                }
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclePortfolio)

            val touchHelperCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN) {
                    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val myUpdate = myUpdateViewHolder(viewHolder)

                        val selectedPortfolio = portfolioList[myUpdate]

                        Log.d("idPortfolio", "${selectedPortfolio.portfolioId}")

                        //updatePortfolio = myUpdate.toString()
                        val intent = Intent(this@MainActivity, UpdateActivity::class.java).apply {
                            putExtra(ITEM, selectedPortfolio.portfolioItem)
                            putExtra(COST, selectedPortfolio.portfolioCost)
                            putExtra(TIME, selectedPortfolio.portfolioTime)
                            putExtra(DATE, selectedPortfolio.portfolioDate)
                            putExtra(UPDATE, selectedPortfolio.portfolioId)
                        }
                        startActivity(intent)
                        Toast.makeText(this@MainActivity, "Edit product...", Toast.LENGTH_LONG).show()
                    }
                }
        val touchHelper = ItemTouchHelper(touchHelperCallback)
        touchHelper.attachToRecyclerView(recyclePortfolio)


        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPortfolio::class.java)
            startActivity(intent)
        }

    }

    override fun onRowLongClicked(position: Int) {
        enableActionMode(position)
    }
      private fun enableActionMode(position: Int){
       startSupportActionMode(ActionModeCallback())

         Log.d("position", "$position")
         positionInt = position.toString()
    }


   private inner class ActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.action_mode, menu)
            return true
        }
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                id.delete -> {
                    val position = positionInt.toInt()
                    val myPortfolio = mAdapter.getPortfolioAtPosition(position)
                    portfolioViewModel.deletePortfolio(myPortfolio)
                    Toast.makeText(this@MainActivity, "Deleting", Toast.LENGTH_LONG).show()
                    mode.finish()
                    true
                }
                id.edit -> {
                    val position = positionInt.toInt()
                    val selectedPortfolio = portfolioList[position]
                    val intent = Intent(this@MainActivity, UpdateActivity::class.java).apply {
                        putExtra(ITEM, selectedPortfolio.portfolioItem)
                        putExtra(COST, selectedPortfolio.portfolioCost)
                        putExtra(TIME, selectedPortfolio.portfolioTime)
                        putExtra(DATE, selectedPortfolio.portfolioDate)
                        putExtra(UPDATE, selectedPortfolio.portfolioId)
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }


   private fun myDeleteViewHolder(viewHolder: RecyclerView.ViewHolder){
        val position = viewHolder.adapterPosition
        val deletePortfolio = portfolioList[position]
        portfolioViewModel.deletePortfolio(deletePortfolio)
    }

    private fun myUpdateViewHolder(viewHolder: RecyclerView.ViewHolder): Int{
        val position = viewHolder.adapterPosition
         mAdapter.getPortfolioAtPosition(position)
        return position
    }

    companion object{
        const val ITEM = "item"
        const val COST = "cost"
        const val TIME = "time"
        const val DATE = "date"
        const val UPDATE = "update"
    }

}
