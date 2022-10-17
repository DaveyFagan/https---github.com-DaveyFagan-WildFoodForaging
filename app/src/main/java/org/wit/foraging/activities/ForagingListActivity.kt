package org.wit.foraging.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.foraging.R
import org.wit.foraging.main.MainApp
import org.wit.foraging.databinding.ActivityForagingListBinding
import org.wit.foraging.databinding.CardForagingBinding
import org.wit.foraging.models.ForagingModel

class ForagingListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityForagingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForagingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ForagingAdapter(app.foragingList)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ForagingActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class ForagingAdapter constructor(private var foragingList: List<ForagingModel>) :
    RecyclerView.Adapter<ForagingAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardForagingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = foragingList[holder.adapterPosition]
        holder.bind(placemark)
    }

    override fun getItemCount(): Int = foragingList.size

    class MainHolder(private val binding : CardForagingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foraging: ForagingModel) {
            binding.foragingPlantNameTitle.text = foraging.name
            binding.foragingPlantScientificName.text = foraging.scientificName
        }
    }
}