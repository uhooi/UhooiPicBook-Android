package com.theuhooi.uhooipicbook.modules.monsterlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_monster_list.view.*

class MonsterListRecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val iconImageView: ImageView = view.icon_imageview
    val nameTextView: TextView = view.name_textview
}
