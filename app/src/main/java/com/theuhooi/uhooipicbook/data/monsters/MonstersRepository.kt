package com.theuhooi.uhooipicbook.data.monsters

import com.theuhooi.uhooipicbook.data.monsters.impl.MonsterItem

interface MonstersRepository {
    fun loadMonsters(
        onSuccess: (monsters: List<MonsterItem>) -> Unit,
        onFailure: (error: Throwable) -> Unit
    )
}

