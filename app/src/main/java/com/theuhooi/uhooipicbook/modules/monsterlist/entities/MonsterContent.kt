package com.theuhooi.uhooipicbook.modules.monsterlist.entities

import android.os.Parcelable
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import kotlinx.android.parcel.Parcelize
import java.lang.Thread.sleep

class MonsterContent(private val monstersRepository: MonstersRepository) {

    // region Stored Instance Properties

    var monsters: List<MonsterItem> = ArrayList()

    // endregion

    // region Initializers

    init {
        this.monstersRepository.loadMonsters(
            onSuccess = { monsters ->
                this.monsters = monsters
            }, onFailure = {
                // TODO: エラーハンドリング
            }
        )
        sleep(10 * 1000) // TODO: スリープしても1件も表示されない
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
