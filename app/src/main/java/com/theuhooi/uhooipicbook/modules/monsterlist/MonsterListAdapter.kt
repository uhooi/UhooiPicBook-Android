package com.theuhooi.uhooipicbook.modules.monsterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theuhooi.uhooipicbook.databinding.ItemMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem

class MonsterListAdapter(
) : ListAdapter<MonsterItem, MonsterListAdapter.MonsterViewHolder>(MonsterItem.DIFF_CALLBACK) {

    // region View Life-Cycle Methods

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonsterViewHolder {
        val binding = ItemMonsterListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MonsterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // endregion

    // region ViewHolder

    class MonsterViewHolder(val binding: ItemMonsterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val onClickListener = View.OnClickListener { view ->
            val item = view.tag as MonsterItem
            val action = MonsterListFragmentDirections.actionListToDetail(item.order)
            view.findNavController().navigate(action)
        }

        fun bind(monster: MonsterItem) {
            binding.monsterItem = monster
            binding.cardView.tag = monster
            binding.cardView.setOnClickListener(onClickListener)
        }
    }

    // endregion

}
