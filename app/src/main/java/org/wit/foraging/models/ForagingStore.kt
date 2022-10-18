package org.wit.foraging.models

interface ForagingStore {
    fun findAll(): List<ForagingModel>
    fun create(foraging: ForagingModel)
    fun update(foragnin: ForagingModel)
}