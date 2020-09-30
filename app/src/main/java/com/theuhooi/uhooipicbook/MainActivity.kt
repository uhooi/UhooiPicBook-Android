package com.theuhooi.uhooipicbook

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.color.MaterialColors
import com.theuhooi.uhooipicbook.extensions.IntColorInterface
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragment
import com.theuhooi.uhooipicbook.modules.monsterlist.MonsterListFragmentDirections
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem

class MainActivity : AppCompatActivity(), MonsterListFragment.OnListFragmentInteractionListener,
    IntColorInterface {

    // region View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        configureToolBar()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu == null) {
            return true
        }
        this.menuInflater.inflate(R.menu.menu_share, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_menu_item -> {
                ShareCompat.IntentBuilder
                    .from(this)
                    .setText("aiueo") // FIXME: 仮の文言
                    .setType("text/plain")
                    .setChooserTitle(R.string.share_menu_item)
                    .startChooser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

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
