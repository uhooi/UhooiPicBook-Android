package com.theuhooi.uhooipicbook

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.CoilUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber

@HiltAndroidApp
class UhooiPicBookApp : Application() {

    // region View Life-Cycle Methods

    override fun onCreate() {
        super.onCreate()

        configureTimber()
        createNotificationChannels()
        initializeCoilImageLoader()
    }

    // endregion

    // region Other Private Methods

    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

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
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 1000, 500, 1000)
        }
    }

    private fun initializeCoilImageLoader() {
        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder(applicationContext))
                } else {
                    add(GifDecoder())
                }
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    // endregion

}
