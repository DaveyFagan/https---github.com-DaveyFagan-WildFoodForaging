package org.wit.foraging.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.foraging.R
import org.wit.foraging.adapters.ForagingAdapter
import org.wit.foraging.adapters.ForagingListener
import org.wit.foraging.main.MainApp
import org.wit.foraging.databinding.ActivityForagingListBinding
import org.wit.foraging.models.ForagingModel

class ForagingListActivity : AppCompatActivity(), ForagingListener {

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
        binding.recyclerView.adapter = ForagingAdapter(app.foragingList.findAll(), this)


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

    override fun onForagingClick(foraging: ForagingModel) {
        val launcherIntent = Intent(this, ForagingActivity::class.java)
        launcherIntent.putExtra("foraging_edit", foraging)
        startActivityForResult(launcherIntent,0)    }
}

