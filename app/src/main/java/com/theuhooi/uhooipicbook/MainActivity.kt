package com.theuhooi.uhooipicbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theuhooi.uhooipicbook.monsterlist.MonsterListFragment
import com.theuhooi.uhooipicbook.monsterlist.dummy.DummyContent

class MainActivity : AppCompatActivity(), MonsterListFragment.OnListFragmentInteractionListener {

    // MARK: View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
    }

}
