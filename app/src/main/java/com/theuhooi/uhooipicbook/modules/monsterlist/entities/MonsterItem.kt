package com.theuhooi.uhooipicbook.modules.monsterlist.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MonsterItem(
    val name: String = "",
    val description: String = "",
    val base_color: String = "", // FIXME: `baseColorCode` にリネームする
    val icon_url: String = "", // FIXME: `iconUrlString` にリネームする
    val dancing_url: String = "", // FIXME: `dancingUrlString` にリネームする
    val order: Int = 0
) : Parcelable
