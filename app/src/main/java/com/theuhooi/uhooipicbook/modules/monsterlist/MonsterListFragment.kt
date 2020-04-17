package com.theuhooi.uhooipicbook.modules.monsterlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.modules.monsterlist.entity.MonsterContent
import com.theuhooi.uhooipicbook.modules.monsterlist.entity.MonsterContent.MonsterItem

class MonsterListFragment : Fragment() {

    // MARK: Stored Instance Properties

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    // MARK: View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            this.columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_monster_list, container, false)
        if (view is RecyclerView) {
            view.layoutManager = when {
                this.columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, this.columnCount)
                }
            view.adapter = MonsterListRecyclerViewAdapter(MonsterContent.monsters, this.listener)
        }

        return view
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

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: MonsterItem?)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int): MonsterListFragment {
            val bundle = Bundle().also { it.putInt(ARG_COLUMN_COUNT, columnCount) }
            return MonsterListFragment().also { it.arguments = bundle }
        }

    }

}
