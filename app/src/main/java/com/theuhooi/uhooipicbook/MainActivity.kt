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
        configureActionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if (this.supportFragmentManager.backStackEntryCount >= 1) {
                    this.supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // MARK: Other Private Methods

    private fun configureActionBar() {
        this.supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment =
                this.supportFragmentManager.findFragmentById(R.id.fragment_container)
            when (currentFragment) {
                is MonsterListFragment -> {
                    this.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                is MonsterDetailFragment -> {
                    this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
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
