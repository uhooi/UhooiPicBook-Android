package com.theuhooi.uhooipicbook.modules.monsterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment.OnListFragmentInteractionListener
import com.theuhooi.uhooipicbook.modules.monsterlist.entity.MonsterContent.MonsterItem
import kotlinx.android.synthetic.main.item_monster_list.view.*

class MonsterListRecyclerViewAdapter(
    private val monsters: List<MonsterItem>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MonsterListRecyclerViewAdapter.ViewHolder>() {

    // MARK: Stored Instance Properties

    private val onClickListener: View.OnClickListener

    // MARK: Initializers

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as MonsterItem
            this.listener?.onListFragmentInteraction(item)
        }
    }

    // MARK: View Life-Cycle Methods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_monster_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = monsters[position]
        holder.iconImageView.load(item.iconUrlString)
        holder.nameTextView.text = item.name

        holder.view.tag = item
        holder.view.setOnClickListener(onClickListener)
    }

    override fun getItemCount(): Int = monsters.size

    // MARK: ViewHolder

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val iconImageView: ImageView = view.icon_imageview
        val nameTextView: TextView = view.name_textview
    }

}
