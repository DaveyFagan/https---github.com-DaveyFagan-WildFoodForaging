package org.wit.foraging.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.foraging.databinding.ActivityForagingBinding
import org.wit.foraging.models.ForagingModel
import timber.log.Timber
import timber.log.Timber.i


class ForagingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForagingBinding
    var foraging = ForagingModel()
    val foragingList = ArrayList<ForagingModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Foraging Activity started..")

        binding.btnAdd.setOnClickListener() {
            foraging.name = binding.foragingPlantName.text.toString()
            foraging.scientificName = binding.foragingPlantScientificName.text.toString()
            foraging.datePicked = binding.foragingDatePicked.text.toString()
            if (foraging.name.isNotEmpty() and foraging.scientificName.isNotEmpty() and foraging.datePicked.isNotEmpty()) {
                foragingList.add(foraging.copy())
                i("add Button Pressed: $foraging.name $foraging.scientificName $foraging.datePicked")
                for (i in foragingList.indices)
                { i("Foraged food[$i]:${this.foragingList[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Fill Out All Fields", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}