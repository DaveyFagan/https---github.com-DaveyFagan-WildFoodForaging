package org.wit.foraging.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForagingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadForaging()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ForagingActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onForagingClick(foraging: ForagingModel) {
        val launcherIntent = Intent(this, ForagingActivity::class.java)
        launcherIntent.putExtra("foraging_edit", foraging)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadForaging() }
    }

    private fun loadForaging() {
        showForingList(app.foragingList.findAll())
    }

    fun showForingList (foragingList: List<ForagingModel>) {
        binding.recyclerView.adapter = ForagingAdapter(foragingList, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}

