package org.wit.foraging.models

import timber.log.Timber.i

class ForagingMemStore: ForagingStore {

    val foragingList  = ArrayList<ForagingModel>()

    override fun findAll(): List<ForagingModel> {
        return foragingList
    }

    override fun create(foraging: ForagingModel) {
        foragingList.add(foraging)
        logAll()
    }

    fun logAll() {
        foragingList.forEach{ i("${it}") }
    }
}