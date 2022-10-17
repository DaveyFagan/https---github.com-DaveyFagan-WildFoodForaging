package org.wit.foraging.main

import android.app.Application
import org.wit.foraging.models.ForagingModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val foragingList = ArrayList<ForagingModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Foraging app started")
    }
}