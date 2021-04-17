package com.theuhooi.uhooipicbook

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.color.MaterialColors
import com.theuhooi.uhooipicbook.extensions.IntColorInterface
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragmentDirections
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MonsterListFragment.OnListFragmentInteractionListener,
    IntColorInterface {

    // region View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        configureToolBar()

        openNotificationUrlIfNeeded()
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
                    ColorDrawable(
                        MaterialColors.getColor(
                            this,
                            R.attr.colorPrimary,
                            "colorPrimary is not set in the current theme"
                        )
                    )
                )
                this.window.statusBarColor = MaterialColors.getColor(
                    this,
                    R.attr.colorPrimaryVariant,
                    "colorPrimaryVariant is not set in the current theme"
                )
            }
        }
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun openNotificationUrlIfNeeded() {
        val url = this.intent.getStringExtra(getString(R.string.notification_url_extra_name))
        if (url != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    // endregion

    // region MonsterListFragment.OnListFragmentInteractionListener

    override fun onListFragmentInteraction(item: MonsterItem) {
        val action = MonsterListFragmentDirections.actionListToDetail(item)
        findNavController(R.id.nav_host_fragment).navigate(action)

        if (item.baseColorCode.isNotEmpty()) {
            val actionBarColor = Color.parseColor(item.baseColorCode)
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(actionBarColor))
            this.window.statusBarColor = actionBarColor.actionBarColorToStatusBarColor
        }
    }

    // endregion

}
