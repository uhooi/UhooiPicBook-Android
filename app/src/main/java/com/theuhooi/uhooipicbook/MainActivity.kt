package com.theuhooi.uhooipicbook

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(item.baseColorCode)))

            var hsl = FloatArray(3)
            ColorUtils.colorToHSL(Color.parseColor(item.baseColorCode), hsl)
            hsl.set(0, hsl[0] - 6) // TODO: 0未満にならないようにする
            hsl.set(2, hsl[2] - 0.09f) // TODO: 0未満にならないようにする
            this.window.statusBarColor = ColorUtils.HSLToColor(hsl)
        }
    }

    // endregion

}
