package org.wit.foraging.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForagingModel(
    var id: Long = 0,
    var name: String = "",
    var scientificName: String = "",
    var datePicked: String = "",
    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f

) : Parcelable

@Parcelize
data class UserModel(
    var id: Long = 0,
    var username: String = "",
    var password: String = "",
    var email: String = "",

    ) : Parcelable


@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable