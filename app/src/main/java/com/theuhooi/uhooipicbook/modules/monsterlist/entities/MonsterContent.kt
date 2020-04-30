package com.theuhooi.uhooipicbook.modules.monsterlist.entities

import android.os.Parcelable
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.runBlocking

class MonsterContent(private val monstersRepository: MonstersRepository) {

    // region Stored Instance Properties

    var monsters: List<MonsterItem> = ArrayList()

    // endregion

    // region Initializers

    init {
        runBlocking {
            monstersRepository.loadMonsters(
                onSuccess = {
                    monsters = it
                }, onFailure = {
                    // TODO: エラーハンドリング
                }
            )
        }
    }

    // endregion

    // region Data Classes

    @Parcelize
    data class MonsterItem(
        val name: String = "",
        val description: String = "",
        val baseColorCode: String = "",
        val iconUrlString: String = "",
        val dancingUrlString: String = "",
        val order: Int = 0
    ) : Parcelable

    // endregion

}
