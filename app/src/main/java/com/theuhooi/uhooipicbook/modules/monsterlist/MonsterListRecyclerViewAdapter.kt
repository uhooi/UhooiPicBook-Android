package com.theuhooi.uhooipicbook.modules.monsterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.theuhooi.uhooipicbook.databinding.ItemMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment.OnListFragmentInteractionListener
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem

class MonsterListRecyclerViewAdapter(
    private val listener: OnListFragmentInteractionListener?,
    private val monsters: LiveData<List<MonsterItem>>,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<MonsterListRecyclerViewAdapter.MonsterListRecyclerViewHolder>() {

    // region Stored Instance Properties

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as MonsterItem
        this.listener?.onListFragmentInteraction(item)
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
        holder.binding.apply {
            lifecycleOwner = viewLifecycleOwner
            val monster = monsters.value?.get(position)
            monsterItem = monster
            cardView.tag = monster
            cardView.setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = this.monsters.value?.size ?: 0

    // endregion

    // region ViewHolder

    class MonsterListRecyclerViewHolder(val binding: ItemMonsterListBinding) : RecyclerView.ViewHolder(binding.root)

    // endregion

}
