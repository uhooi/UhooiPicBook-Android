package com.theuhooi.uhooipicbook.modules.monsterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment.OnListFragmentInteractionListener
import com.theuhooi.uhooipicbook.modules.monsterlist.entity.MonsterContent.MonsterItem

class MonsterListRecyclerViewAdapter(
    private val monsters: List<MonsterItem>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MonsterListRecyclerViewHolder>() {

    // region Stored Instance Properties

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as MonsterItem
        this.listener?.onListFragmentInteraction(item)
    }

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterListRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_monster_list, parent, false)
        return MonsterListRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonsterListRecyclerViewHolder, position: Int) {
        val item = this.monsters[position]
        holder.iconImageView.load(item.iconUrlString)
        holder.nameTextView.text = item.name

        holder.view.tag = item
        holder.view.setOnClickListener(onClickListener)
    }

    override fun getItemCount(): Int = this.monsters.size

    // endregion

}
