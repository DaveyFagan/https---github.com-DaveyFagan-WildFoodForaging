package org.wit.foraging.models

interface ForagingStore {
    fun findAll(): List<ForagingModel>
    fun create(foraging: ForagingModel)
    fun update(foraging: ForagingModel)
    fun delete(foraging: ForagingModel)

}