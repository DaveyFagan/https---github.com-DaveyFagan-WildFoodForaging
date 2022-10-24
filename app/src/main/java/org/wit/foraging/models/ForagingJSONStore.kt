package org.wit.foraging.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.foraging.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "foraging.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<ForagingModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class ForagingJSONStore(private val context: Context) : ForagingStore {

    var foragingList = mutableListOf<ForagingModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ForagingModel> {
        logAll()
        return foragingList
    }

    override fun create(foraging: ForagingModel) {
        foraging.id = generateRandomId()
        foragingList.add(foraging)
        serialize()
    }


    override fun update(foraging: ForagingModel) {
        var foundForaging: ForagingModel? = foragingList.find { p -> p.id == foraging.id }
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
        serialize()
    }

    override fun delete(foraging: ForagingModel) {
        foragingList.remove(foraging)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(foragingList, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        foragingList = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        foragingList.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}