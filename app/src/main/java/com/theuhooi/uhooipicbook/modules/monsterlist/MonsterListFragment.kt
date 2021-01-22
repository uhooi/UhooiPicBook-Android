package com.theuhooi.uhooipicbook.modules.monsterlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentMonsterListBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels.MonsterListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonsterListFragment : Fragment() {

    // region Stored Instance Properties

    private var listener: OnListFragmentInteractionListener? = null

    private val viewModel: MonsterListViewModel by viewModels()

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding = FragmentMonsterListBinding.inflate(inflater, container, false)
        binding.monsterListRecyclerview.adapter =
            MonsterListRecyclerViewAdapter(
                this.listener,
                this.viewModel.monsters,
                this.viewLifecycleOwner
            )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_monster_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_nav_licenses -> {
                findNavController().navigate(MonsterListFragmentDirections.actionListToLicenses())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // endregion

    // region Interfaces

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: MonsterItem)
    }

    // endregion

}
