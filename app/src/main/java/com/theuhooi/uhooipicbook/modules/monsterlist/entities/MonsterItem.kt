package com.theuhooi.uhooipicbook.modules.monsterlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MonsterItem(
    val name: String = "",
    val description: String = "",
    val base_color: String = "",
    val icon_url: String = "",
    val dancing_url: String = "",
    val order: Int = 0
) : Parcelable
