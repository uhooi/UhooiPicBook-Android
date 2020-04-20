package com.theuhooi.uhooipicbook

import android.os.Bundle
import android.view.MenuItem
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

        this.supportFragmentManager.addOnBackStackChangedListener {
            if (this.supportFragmentManager.getBackStackEntryCount() >= 1) {
                this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if (this.supportFragmentManager.backStackEntryCount >= 1) {
                    this.supportFragmentManager.popBackStack()
                    return true
                } else {
                    finish()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // MARK: MonsterListFragment.OnListFragmentInteractionListener

    override fun onListFragmentInteraction(item: MonsterContent.MonsterItem?) {
        if (item == null) {
            return
        }
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, MonsterDetailFragment.newInstance(item))
        transaction.commit()
    }

}
