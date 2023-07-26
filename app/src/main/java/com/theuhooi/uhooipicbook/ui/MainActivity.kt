package com.theuhooi.uhooipicbook.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.color.MaterialColors
import com.theuhooi.uhooipicbook.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // region View Life-Cycle Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
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
                supportActionBar?.setBackgroundDrawable(
                    ColorDrawable(
                        MaterialColors.getColor(
                            this,
                            androidx.appcompat.R.attr.colorPrimary,
                            "colorPrimary is not set in the current theme"
                        )
                    )
                )
                window.statusBarColor = MaterialColors.getColor(
                    this,
                    com.google.android.material.R.attr.colorPrimaryVariant,
                    "colorPrimaryVariant is not set in the current theme"
                )
            }
        }
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun openNotificationUrlIfNeeded() {
        val urlString = intent.getStringExtra(getString(R.string.notification_url_extra_name))
        if (urlString != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
            startActivity(intent)
        }
    }

    // endregion

}
