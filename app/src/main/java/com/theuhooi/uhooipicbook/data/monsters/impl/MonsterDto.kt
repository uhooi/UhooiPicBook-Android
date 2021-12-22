package com.theuhooi.uhooipicbook.data.monsters.impl

import com.google.firebase.firestore.PropertyName

data class MonsterDto(
    val name: String = "",
    val description: String = "",
    @get:PropertyName("base_color")
    @set:PropertyName("base_color")
    var baseColorCode: String = "",
    @get:PropertyName("icon_url")
    @set:PropertyName("icon_url")
    var iconUrlString: String = "",
    @get:PropertyName("dancing_url")
    @set:PropertyName("dancing_url")
    var dancingUrlString: String = "",
    val order: Int = 0
)
