package org.wit.foraging.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForagingModel(var name: String = "",
                         var scientificName: String = "",
                         var datePicked: String = "",
) : Parcelable
