package com.theuhooi.uhooipicbook.data.monsters

import com.theuhooi.uhooipicbook.data.monsters.impl.MonsterDto

interface MonstersRepository {
    fun loadMonsters(
        onSuccess: (monsters: List<MonsterDto>) -> Unit,
        onFailure: (error: Throwable) -> Unit
    )
}
