package org.wit.foraging.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.foraging.databinding.ActivityForagingBinding
import org.wit.foraging.main.MainApp
import org.wit.foraging.models.ForagingModel
import timber.log.Timber.i


class ForagingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForagingBinding
    var foraging = ForagingModel()
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForagingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener() {
            foraging.name = binding.foragingPlantName.text.toString()
            foraging.scientificName = binding.foragingPlantScientificName.text.toString()
            foraging.datePicked = binding.foragingDatePicked.text.toString()
            if (foraging.name.isNotEmpty() and foraging.scientificName.isNotEmpty() and foraging.datePicked.isNotEmpty()) {
                app.foragingList.add(foraging.copy())
                i("add Button Pressed: $foraging.name $foraging.scientificName $foraging.datePicked")
                for (i in app.foragingList.indices)
                { i("Foraged food[$i]:${this.app.foragingList[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Fill Out All Fields", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}