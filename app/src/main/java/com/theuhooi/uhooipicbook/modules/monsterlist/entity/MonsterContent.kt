package com.theuhooi.uhooipicbook.modules.monsterlist.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

object MonsterContent {

    // region Stored Instance Properties

    val monsters: MutableList<MonsterItem> = ArrayList()

    // endregion

    // region Initializers

    init {
        // TODO: Firebaseからデータを取得する
        for (i in 1..25) {
            this.monsters.add(createMonsterItem(i))
        }
    }

    // endregion

    // region Other Private Methods

    // TODO: Firebaseからデータを取得したらメソッドごと削除する
    private fun createMonsterItem(order: Int): MonsterItem {
        return MonsterItem(
            "uhooi",
            "ゆかいな　みどりの　せいぶつ。\nわるそうに　みえるが　むがい。",
            "#81D674",
            "https://pbs.twimg.com/profile_images/1210051005032747008/d-y15OUa_400x400.jpg",
            order
        )
    }

    // endregion

    // region Data Classes

    @Parcelize
    data class MonsterItem(
        val name: String,
        val description: String,
        val baseColorCode: String,
        val iconUrlString: String,
        val order: Int
    ) : Parcelable

    // endregion

}
