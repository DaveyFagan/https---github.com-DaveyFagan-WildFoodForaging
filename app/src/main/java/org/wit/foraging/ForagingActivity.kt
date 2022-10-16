package org.wit.foraging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import timber.log.Timber.i

class ForagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foraging)
        Timber.plant(Timber.DebugTree())
        i("Foraging Activity started..")


    }
}