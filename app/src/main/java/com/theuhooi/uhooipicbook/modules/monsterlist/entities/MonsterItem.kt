package com.theuhooi.uhooipicbook.modules.monsterlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MonsterItem(
    val name: String = "",
    val description: String = "",
    val baseColorCode: String = "",
    val iconUrlString: String = "",
    val dancingUrlString: String = "",
    val order: Int = 0
) : Parcelable
