package org.wit.foraging.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForagingModel(var id: Long = 0,
                         var name: String = "",
                         var scientificName: String = "",
                         var datePicked: String = "",
) : Parcelable
