package org.wit.foraging.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ForagingMemStore : ForagingStore {

    val foragingList = ArrayList<ForagingModel>()
    val userList = ArrayList<UserModel>()

    override fun findAll(): List<ForagingModel> {
        return foragingList
    }

    override fun findAllUsers(): List<UserModel> {
        return userList
    }

    override fun create(foraging: ForagingModel) {
        foraging.id = getId()
        foragingList.add(foraging)
        logAll()
    }

    override fun createUser(user: UserModel) {
        user.id = getId()
        userList.add(user)
        logAll()
    }

    override fun update(foraging: ForagingModel) {
        val foundForaging: ForagingModel? = foragingList.find { p -> p.id == foraging.id }
        if (foundForaging != null) {
            foundForaging.name = foraging.name
            foundForaging.scientificName = foraging.scientificName
            foundForaging.datePicked = foraging.datePicked
            foundForaging.image = foraging.image
            foundForaging.lat = foraging.lat
            foundForaging.lng = foraging.lng
            foundForaging.zoom = foraging.zoom
            logAll()
        }
    }

    fun logAll() {
        foragingList.forEach { i("${it}") }
    }

    override fun delete(foraging: ForagingModel) {
        foragingList.remove(foraging)
    }
}