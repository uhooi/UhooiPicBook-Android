package com.theuhooi.uhooipicbook.modules.monsterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.modules.monsterlist.entity.MonsterContent

class MonsterDetailFragment : Fragment() {

    // MARK: Stored Instance Properties

    val args: MonsterDetailFragmentArgs by navArgs()
    private var monster: MonsterContent.MonsterItem? = null

    // MARK: View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monster_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.monster = this.args.monster
    }

}
