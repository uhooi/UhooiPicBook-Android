package com.theuhooi.uhooipicbook

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class MainApplication : Application() {

    // region View Life-Cycle Methods

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    // endregion

    // region Other Private Methods

    private fun createNotificationChannel() {
        createInfoNotificationChannel()
    }

    private fun createInfoNotificationChannel() {
        val id = getString(R.string.info_notification_channel_id)
        val name = getString(R.string.info_notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance).apply {
            description = getString(R.string.info_notification_channel_description)
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // endregion

}