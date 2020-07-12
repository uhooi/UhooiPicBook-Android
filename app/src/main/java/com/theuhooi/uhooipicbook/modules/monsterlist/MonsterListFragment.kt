package com.theuhooi.uhooipicbook.modules.monsterlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentMonsterListBinding
import com.theuhooi.uhooipicbook.databinding.ItemMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterListViewModel
import com.theuhooi.uhooipicbook.util.OnListFragmentInteractionListener

class MonsterListFragment : Fragment() {

    // region Stored Instance Properties

    private var listener: OnListFragmentInteractionListener? = null

    private val viewModel: MonsterListViewModel by navGraphViewModels(R.id.nav_graph)

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val binding = FragmentMonsterListBinding.inflate(inflater, container, false)
        binding.monsterListRecyclerview.adapter = MonsterListRecyclerAdapter()
        binding.monsterListRecyclerview.layoutManager = LinearLayoutManager(this.context)
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnListFragmentInteractionListener) {
            this.listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()

        this.listener = null
    }

    // endregion

    // region View Life-Cycle Methods

    private inner class MonsterListRecyclerAdapter: RecyclerView.Adapter<MonsterListRecyclerViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MonsterListRecyclerViewHolder = ItemMonsterListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let {
            MonsterListRecyclerViewHolder(it)
        }

        override fun onBindViewHolder(holder: MonsterListRecyclerViewHolder, position: Int) {
            holder.binding.apply {
                lifecycleOwner = viewLifecycleOwner
                val monster = viewModel.monsters.value?.get(position)
                monsterItem = monster
                cardView.tag = monster
                cardView.setOnClickListener { v ->
                    viewModel.selectMonsterAt(position)
                    val item = v.tag as MonsterItem
                    listener?.onListFragmentInteraction(item)
                    findNavController().navigate(R.id.action_list_to_detail)

                }
            }
        }

        override fun getItemCount(): Int = viewModel.monsters.value?.size ?: 0

    }

    // endregion

    // region ViewHolder

    class MonsterListRecyclerViewHolder(val binding: ItemMonsterListBinding) : RecyclerView.ViewHolder(binding.root)

    // endregion
}
