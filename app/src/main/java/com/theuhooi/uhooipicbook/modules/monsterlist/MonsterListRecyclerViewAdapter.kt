package com.theuhooi.uhooipicbook.modules.monsterlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.theuhooi.uhooipicbook.databinding.ItemMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment.OnListFragmentInteractionListener
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodel.MonsterListViewModel

class MonsterListRecyclerViewAdapter(
    private val listener: OnListFragmentInteractionListener?,
    private val viewModel: MonsterListViewModel,
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
    ): MonsterListRecyclerViewHolder = ItemMonsterListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false).let {
        MonsterListRecyclerViewHolder(it)
    }

    override fun onBindViewHolder(holder: MonsterListRecyclerViewHolder, position: Int) {
        holder.binding.apply {
            lifecycleOwner = viewLifecycleOwner
            monsterItem = viewModel.monsterList.value?.get(position)
            cardView.tag = viewModel.monsterList.value?.get(position)
            cardView.setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = viewModel.monsterList.value?.size ?: 0

    // endregion

    // region ViewHolder

    class MonsterListRecyclerViewHolder(val binding: ItemMonsterListBinding) : RecyclerView.ViewHolder(binding.root)

    // endregion

}
