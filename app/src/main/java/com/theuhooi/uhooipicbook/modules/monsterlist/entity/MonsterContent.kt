package com.theuhooi.uhooipicbook.modules.monsterlist.entity

import java.util.*

object MonsterContent {

    // MARK: Stored Instance Properties

    val monsters: MutableList<MonsterItem> = ArrayList()

    // MARK: Initializers

    init {
        // TODO: Firebaseからデータを取得する
        for (i in 1..25) {
            this.monsters.add(createMonsterItem(i))
        }
    }

    // MARK: Other Private Methods

    // TODO: Firebaseからデータを取得したらメソッドごと削除する
    private fun createMonsterItem(order: Int): MonsterItem {
        return MonsterItem(
            "uhooi",
            "でぃすくりぷしょん\nてすと",
            "#81D674",
            "https://yahoo.co.jp",
            "https://yahoo.co.jp",
            order
        )
    }

    // MARK: MonsterItem

    data class MonsterItem(
        val name: String,
        val description: String,
        val baseColorCode: String,
        val iconUrlString: String,
        val dancingUrlString: String,
        val order: Int
    )

}
