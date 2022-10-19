package org.wit.foraging.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.foraging.R
import org.wit.foraging.databinding.ActivityForagingBinding
import org.wit.foraging.main.MainApp
import org.wit.foraging.models.ForagingModel
import timber.log.Timber.i

import android.app.DatePickerDialog
import android.widget.EditText
import java.util.*


class ForagingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForagingBinding
    var foraging = ForagingModel()
    lateinit var app: MainApp
    lateinit var dateEdt: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        var edit = false
        dateEdt = findViewById(R.id.foragingDatePicked)

        if (intent.hasExtra("foraging_edit")) {
            edit = true
            foraging = intent.extras?.getParcelable("foraging_edit")!!
            binding.foragingPlantName.setText(foraging.name)
            binding.foragingPlantScientificName.setText(foraging.scientificName)
            binding.foragingDatePicked.setText(foraging.datePicked)
            binding.btnAdd.setText(R.string.save_foraging)
        }

        binding.btnAdd.setOnClickListener() {
            foraging.name = binding.foragingPlantName.text.toString()
            foraging.scientificName = binding.foragingPlantScientificName.text.toString()
            foraging.datePicked = binding.foragingDatePicked.text.toString()
            if (foraging.name.isEmpty() and foraging.scientificName.isEmpty() and foraging.datePicked.isEmpty()) {
                Snackbar
                    .make(it, R.string.enter_all_fields, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.foragingList.update(foraging.copy())
                } else {
                    app.foragingList.create(foraging.copy())
                }
            }
            i("add Button Pressed: $foraging.name $foraging.scientificName $foraging.datePicked")
            setResult(RESULT_OK)
            finish()
        }


        dateEdt.setOnClickListener {

            val c = Calendar.getInstance()


            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(

                this,
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    dateEdt.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_foraging, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}