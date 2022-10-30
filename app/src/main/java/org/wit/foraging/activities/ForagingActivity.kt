package org.wit.foraging.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.foraging.R
import org.wit.foraging.databinding.ActivityForagingBinding
import org.wit.foraging.helpers.showImagePicker
import org.wit.foraging.main.MainApp
import org.wit.foraging.models.ForagingModel
import org.wit.foraging.models.Location
import timber.log.Timber.i
import java.util.*

class ForagingActivity : AppCompatActivity() {

    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityForagingBinding
    var foraging = ForagingModel()
    lateinit var app: MainApp
    lateinit var dateEdt: EditText
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    val REQUEST_IMAGE_CAPTURE = 1

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
            Picasso.get()
                .load(foraging.image)
                .into(binding.foragingImage)
            if (foraging.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_foraging_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            foraging.name = binding.foragingPlantName.text.toString()
            foraging.scientificName = binding.foragingPlantScientificName.text.toString()
            foraging.datePicked = binding.foragingDatePicked.text.toString()
            if (foraging.name.isEmpty() or foraging.scientificName.isEmpty() or foraging.datePicked.isEmpty()) {
                Snackbar
                    .make(it, R.string.enter_all_fields, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.foragingList.update(foraging.copy())
                } else {
                    app.foragingList.create(foraging.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.chooseImage.setOnClickListener {
            i("Select image")
            showImagePicker(imageIntentLauncher)
        }

        binding.takeImage.setOnClickListener {
            i("Take image")
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
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

        binding.foragingLocation.setOnClickListener {
            i("Set Location Pressed")
            val location =
                Location(53.45728574193019, -6.238869520651969, 15f)

            if (foraging.zoom != 0f) {
                location.lat = foraging.lat
                location.lng = foraging.lng
                location.zoom = foraging.zoom
            }
            val launcherIntent = Intent(this, MapsActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            var takenImage = data?.extras?.get("data") as Bitmap
            MediaStore.Images.Media.insertImage(
                getContentResolver(),
                takenImage,
                "image.jpg",
                "foraging image"
            );
        } else {
            super.onActivityResult(requestCode, resultCode, data)
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
            R.id.item_delete -> {
                app.foragingList.delete(foraging.copy())
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            foraging.image = result.data!!.data!!
                            Picasso.get()
                                .load(foraging.image)
                                .into(binding.foragingImage)
                            binding.chooseImage.setText(R.string.change_foraging_image)

                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            foraging.lat = location.lat
                            foraging.lng = location.lng
                            foraging.zoom = location.zoom
                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

}