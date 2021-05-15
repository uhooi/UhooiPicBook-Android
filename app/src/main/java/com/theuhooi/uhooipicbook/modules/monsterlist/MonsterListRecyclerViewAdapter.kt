package com.theuhooi.uhooipicbook.modules.monsterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.theuhooi.uhooipicbook.databinding.ItemMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem

class MonsterListRecyclerViewAdapter(
    private val monsters: List<MonsterItem>,
) : RecyclerView.Adapter<MonsterListRecyclerViewAdapter.MonsterListRecyclerViewHolder>() {

    // region Stored Instance Properties

    private val onClickListener = View.OnClickListener { view ->
        val item = view.tag as MonsterItem
        val action = MonsterListFragmentDirections.actionListToDetail(item.order)
        view.findNavController().navigate(action)
    }

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonsterListRecyclerViewHolder {
        val binding = ItemMonsterListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MonsterListRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonsterListRecyclerViewHolder, position: Int) {
        val monster = this.monsters[position]
        holder.binding.monsterItem = monster
        holder.binding.cardView.tag = monster
        holder.binding.cardView.setOnClickListener(this.onClickListener)
    }

    override fun getItemCount(): Int = this.monsters.size

    // endregion

    // region ViewHolder

    class MonsterListRecyclerViewHolder(val binding: ItemMonsterListBinding) :
        RecyclerView.ViewHolder(binding.root)

    // endregion

}
