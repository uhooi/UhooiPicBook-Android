package com.theuhooi.uhooipicbook.modules.monsterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.theuhooi.uhooipicbook.databinding.ItemMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem

class MonsterListRecyclerViewAdapter(
    private val monsters: LiveData<List<MonsterItem>>,
    private val viewLifecycleOwner: LifecycleOwner
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
        holder.binding.lifecycleOwner = this.viewLifecycleOwner
        val monster = this.monsters.value?.get(position)
        holder.binding.monsterItem = monster
        holder.binding.cardView.tag = monster
        holder.binding.cardView.setOnClickListener(this.onClickListener)
    }

    override fun getItemCount(): Int = this.monsters.value?.size ?: 0

    // endregion

    // region ViewHolder

    class MonsterListRecyclerViewHolder(val binding: ItemMonsterListBinding) :
        RecyclerView.ViewHolder(binding.root)

    // endregion

}
