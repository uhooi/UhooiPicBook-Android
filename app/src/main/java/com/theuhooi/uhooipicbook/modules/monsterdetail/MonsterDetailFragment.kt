package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.api.load
import com.google.android.material.tabs.TabLayoutMediator
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentMonsterDetailBinding
import com.theuhooi.uhooipicbook.databinding.ItemMonsterDetailBinding
import com.theuhooi.uhooipicbook.modules.monsterlist.viewmodel.MonsterListViewModel
import kotlinx.android.synthetic.main.fragment_monster_detail.view.*

class MonsterDetailFragment : Fragment() {

    // region Stored Instance Properties

    private val args: MonsterDetailFragmentArgs by navArgs()

    private val viewModel: MonsterListViewModel by navGraphViewModels(R.id.nav_graph)

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMonsterDetailBinding.inflate(inflater, container, false).let {
        it.monsterViewPager.apply {
            adapter = MonsterDetailAdapter()
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            TabLayoutMediator(it.tabLayout, this, false, true) { _, _ ->
                // do nothing
            }.attach()

            setPageTransformer { view, position ->
                view.translationX = position * 100
            }

            setCurrentItem(viewModel.selectedMonsterItemPosition.value ?: 0, false)
        }
        it.lifecycleOwner = viewLifecycleOwner
        it.root
    }

    // endregion

    // region RecyclerView Adapter

    private inner class MonsterDetailAdapter : RecyclerView.Adapter<MonsterDetailViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterDetailViewHolder = ItemMonsterDetailBinding.inflate(
            LayoutInflater.from(context), parent, false).let {
            MonsterDetailViewHolder(it)
        }

        override fun onBindViewHolder(holder: MonsterDetailViewHolder, position: Int) {
            holder.binding.also {
                it.monsterItem = viewModel.monsterList.value?.get(position)
                it.lifecycleOwner = viewLifecycleOwner
            }
        }

        override fun getItemCount(): Int = viewModel.monsterList.value?.size ?: 0
    }

    // endregion

    // region ViewHolder

    private inner class MonsterDetailViewHolder(val binding: ItemMonsterDetailBinding): RecyclerView.ViewHolder(binding.root)

    // endregion

}
