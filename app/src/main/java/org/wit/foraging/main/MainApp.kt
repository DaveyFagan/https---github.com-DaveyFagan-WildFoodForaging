package org.wit.foraging.main

import android.app.Application
import org.wit.foraging.models.ForagingJSONStore
//import org.wit.foraging.models.ForagingMemStore
import org.wit.foraging.models.ForagingStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var foragingList: ForagingStore
    lateinit var userList: ForagingStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Foraging app started")
        foragingList = ForagingJSONStore(applicationContext)
        userList = ForagingJSONStore(applicationContext)
//          foragingList = ForagingJSONStore(applicationContext)
//        foragingList.add(ForagingModel("One", "About one...", "sdfasad"))
//        foragingList.add(ForagingModel("Two", "About two...", "sdhfiod"))
//        foragingList.add(ForagingModel("Three", "About three...", "klsdjfk"))
    }
}