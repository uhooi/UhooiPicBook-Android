package com.theuhooi.uhooipicbook.domain.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.theuhooi.uhooipicbook.data.monsters.impl.MonsterDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonsterItem(
    val name: String,
    val description: String,
    val baseColorCode: String,
    val iconUrlString: String,
    val dancingUrlString: String,
    val order: Int
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

        fun create(dto: MonsterDto): MonsterItem {
            return MonsterItem(
                dto.name,
                dto.description,
                dto.baseColorCode,
                dto.iconUrlString,
                dto.dancingUrlString,
                dto.order
            )
        }
    }
}
