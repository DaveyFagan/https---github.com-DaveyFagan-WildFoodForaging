package org.wit.foraging.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ForagingMemStore: ForagingStore {

    val foragingList  = ArrayList<ForagingModel>()

    override fun findAll(): List<ForagingModel> {
        return foragingList
    }

    override fun create(foraging: ForagingModel) {
        foraging.id = getId()
        foragingList.add(foraging)
        logAll()
    }

    override fun update(foraging: ForagingModel) {
        var foundForaging: ForagingModel? = foragingList.find { p -> p.id == foraging.id }
        if (foundForaging != null) {
            foundForaging.name = foraging.name
            foundForaging.scientificName = foraging.scientificName
            foundForaging.datePicked = foraging.datePicked
            logAll()
        }
    }

    fun logAll() {
        foragingList.forEach{ i("${it}") }
    }
}