package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.modules.monsterlist.entity.MonsterContent

private const val MONSTER_ARG_PARAM = "monster"

class MonsterDetailFragment : Fragment() {

    // MARK: Stored Instance Properties

    private var monster: MonsterContent.MonsterItem? = null

    // MARK: View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            this.monster = it.getParcelable<MonsterContent.MonsterItem>(MONSTER_ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monster_detail, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(monster: MonsterContent.MonsterItem): MonsterDetailFragment {
            val bundle = Bundle().also { it.putParcelable(MONSTER_ARG_PARAM, monster) }
            return MonsterDetailFragment().also { it.arguments = bundle }
        }
    }

}
