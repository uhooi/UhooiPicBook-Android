package com.theuhooi.uhooipicbook.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.theuhooi.uhooipicbook.MainActivity
import com.theuhooi.uhooipicbook.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // region Stored Instance Properties

    private val infoNotificationId = 0

    // endregion

    // region FirebaseMessagingService

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val message = p0.data["data"].toString()
        sendInfoNotification(message)
    }

    // endregion

    // region Other Private Methods

    private fun sendInfoNotification(message: String) {
        sendNotification(message, getString(R.string.info_notification_channel_id), this.infoNotificationId)
    }

    private fun sendNotification(message: String, channelId: String, notificationId: Int) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_notification)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .build()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}
