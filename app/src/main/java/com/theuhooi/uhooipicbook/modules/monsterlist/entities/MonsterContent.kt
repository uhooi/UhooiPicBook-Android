package com.theuhooi.uhooipicbook.modules.monsterlist.entities

import android.os.Parcelable
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import kotlinx.android.parcel.Parcelize
import java.lang.Thread.sleep

class MonsterContent(private val monstersRepository: MonstersRepository) {

    // MARK: Stored Instance Properties

    var monsters: List<MonsterItem> = ArrayList()

    // MARK: Initializers

    init {
        this.monstersRepository.loadMonsters(
            onSuccess = { monsters ->
                this.monsters = monsters
            }, onFailure = { _ ->
                // TODO: エラーハンドリング
            }
        )
        sleep(10 * 1000) // TODO: スリープしても1件も表示されない
    }

    // MARK: Data Classes

    @Parcelize
    data class MonsterItem(
        val name: String = "",
        val description: String = "",
        val baseColorCode: String = "",
        val iconUrlString: String = "",
        val dancingUrlString: String = "",
        val order: Int = 0
    ) : Parcelable

}
