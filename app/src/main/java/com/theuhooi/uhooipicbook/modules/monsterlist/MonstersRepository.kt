package com.theuhooi.uhooipicbook.modules.monsterlist

import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem

interface MonstersRepository {
    fun loadMonsters(
        onSuccess: (monsters: List<MonsterItem>) -> Unit,
        onFailure: (error: Throwable) -> Unit
    )
}

