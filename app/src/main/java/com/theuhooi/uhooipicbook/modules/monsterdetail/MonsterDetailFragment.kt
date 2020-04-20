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

    private var param: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param = it.getString(MONSTER_ARG_PARAM)
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
