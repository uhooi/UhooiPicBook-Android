package com.theuhooi.uhooipicbook.data.monsters

import com.theuhooi.uhooipicbook.data.Result
import com.theuhooi.uhooipicbook.data.monsters.impl.MonsterDto

interface MonstersRepository {
    suspend fun fetchMonsters(): Result<List<MonsterDto>>
}
