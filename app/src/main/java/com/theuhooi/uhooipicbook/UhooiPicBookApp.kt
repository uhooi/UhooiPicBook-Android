package com.theuhooi.uhooipicbook

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class UhooiPicBookApp : Application() {

    // region View Life-Cycle Methods

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    // endregion

    // region Other Private Methods

    private fun createNotificationChannels() {
        val channels = listOf(
            createInfoNotificationChannel()
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        channels.forEach { notificationManager.createNotificationChannel(it) }
    }

    private fun createInfoNotificationChannel(): NotificationChannel {
        val id = getString(R.string.info_notification_channel_id)
        val name = getString(R.string.info_notification_channel_name)
        val description = getString(R.string.info_notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        return createNotificationChannel(id, name, description, importance)
    }

    private fun createNotificationChannel(
        id: String,
        name: String,
        descriptionText: String,
        importance: Int
    ): NotificationChannel {
        return NotificationChannel(id, name, importance).apply {
            description = descriptionText
        }
    }

    // endregion

}
