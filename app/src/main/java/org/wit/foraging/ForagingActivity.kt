package org.wit.foraging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.foraging.databinding.ActivityForagingBinding
import timber.log.Timber
import timber.log.Timber.i

private lateinit var binding: ActivityForagingBinding

class ForagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Foraging Activity started..")

        binding.btnAdd.setOnClickListener() {
            val plantName = binding.foragingPlantName.text.toString()
            val plantScientificName = binding.foragingPlantScientificName.text.toString()
            val datePicked = binding.foragingDatePicked.text.toString()
            if (plantName.isNotEmpty() and plantScientificName.isNotEmpty() and datePicked.isNotEmpty()) {
                i("add Button Pressed: $plantName $plantScientificName $datePicked")
            }
            else {
                Snackbar
                    .make(it,"Please Fill Out All Fields", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}