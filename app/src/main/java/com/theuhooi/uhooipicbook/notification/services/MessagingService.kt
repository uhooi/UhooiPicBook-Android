package com.theuhooi.uhooipicbook.notification.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.theuhooi.uhooipicbook.ui.MainActivity
import com.theuhooi.uhooipicbook.R

class MessagingService : FirebaseMessagingService() {

    // region FirebaseMessagingService

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let {
            sendInfoNotification(
                it.title ?: getString(R.string.app_name),
                it.body ?: "",
                message.data["url"]
            )
        }
    }

    // endregion

    // region Other Private Methods

    private fun sendInfoNotification(title: String, text: String, urlString: String? = null) {
        sendNotification(
            title,
            text,
            getString(R.string.info_notification_channel_id),
            INFO_NOTIFICATION_ID,
            urlString
        )
    }

    private fun sendNotification(
        title: String,
        text: String,
        channelId: String,
        notificationId: Int,
        urlString: String?
    ) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (urlString != null) {
                putExtra(getString(R.string.notification_url_extra_name), urlString)
            }
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(0, 1000, 500, 1000))
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    // endregion

    // region Companion Object

    companion object {
        const val INFO_NOTIFICATION_ID = 0
    }

    // endregion

}
