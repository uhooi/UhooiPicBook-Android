package com.theuhooi.uhooipicbook.modules.monsterlist.entities

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonsterItem(
    val name: String = "",
    val description: String = "",
    @get:PropertyName("base_color")
    @set:PropertyName("base_color")
    var baseColorCode: String = "",
    @get:PropertyName("icon_url")
    @set:PropertyName("icon_url")
    var iconUrlString: String = "",
    @get:PropertyName("dancing_url")
    @set:PropertyName("dancing_url")
    var dancingUrlString: String = "",
    val order: Int = 0
) : Parcelable {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MonsterItem>() {
            override fun areItemsTheSame(oldItem: MonsterItem, newItem: MonsterItem): Boolean {
                return oldItem.order == newItem.order
            }

            override fun areContentsTheSame(oldItem: MonsterItem, newItem: MonsterItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
