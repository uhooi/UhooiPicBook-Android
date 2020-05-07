package com.theuhooi.uhooipicbook

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.theuhooi.uhooipicbook.extensions.actionBarColorToStatusBarColor
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragmentDirections
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem

class MainActivity : AppCompatActivity(), MonsterListFragment.OnListFragmentInteractionListener {

    // region View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        configureToolBar()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    // endregion

    // region Other Private Methods

    private fun configureToolBar() {
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.monster_list_fragment) {
                this.supportActionBar?.setBackgroundDrawable(
                    ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary))
                )
                this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            }
        }
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    // endregion

    // region MonsterListFragment.OnListFragmentInteractionListener

    override fun onListFragmentInteraction(item: MonsterItem) {
        val action = MonsterListFragmentDirections.actionListToDetail(item)
        findNavController(R.id.nav_host_fragment).navigate(action)

        if (item.baseColorCode.isNotEmpty()) {
            val actionBarColor = Color.parseColor(item.baseColorCode)
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(actionBarColor))
            this.window.statusBarColor = actionBarColor.actionBarColorToStatusBarColor()
        }
    }

    // endregion

}
