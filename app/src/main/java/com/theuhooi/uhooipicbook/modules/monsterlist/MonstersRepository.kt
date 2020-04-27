package com.theuhooi.uhooipicbook.modules.monsterlist

import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterContent

interface MonstersRepository {
    fun loadMonsters(
        onSuccess: (monsters: List<MonsterContent.MonsterItem>) -> Unit,
        onFailure: (error: Throwable) -> Unit
    )
}

