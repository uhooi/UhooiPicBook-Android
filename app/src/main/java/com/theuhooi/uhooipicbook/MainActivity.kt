package com.theuhooi.uhooipicbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.theuhooi.uhooipicbook.modules.monsterdetail.MonsterDetailFragment
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment
import com.theuhooi.uhooipicbook.modules.monsterlist.entity.MonsterContent

class MainActivity : AppCompatActivity(), MonsterListFragment.OnListFragmentInteractionListener {

    // MARK: View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // MARK: MonsterListFragment.OnListFragmentInteractionListener

    override fun onListFragmentInteraction(item: MonsterContent.MonsterItem?) {
        if (item == null) {
            return
        }
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack(null)
        transaction.replace(R.id.monster_list_fragment, MonsterDetailFragment.newInstance(item))
        transaction.commit()
    }

}
