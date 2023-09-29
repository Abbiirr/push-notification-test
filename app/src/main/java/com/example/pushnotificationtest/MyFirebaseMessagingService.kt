package com.example.pushnotificationtest;
import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM messages here
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.notification != null) {
            Log.d(TAG, "Notification Message Body: ${remoteMessage.notification?.body}")

            // Display the notification when the app is in the foreground
            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private fun showNotification(title: String?, body: String?) {
        // Create a notification channel (if not already created)
        createNotificationChannel()

        // Create a notification with the channel ID
        val channelId = "my_channel_id" // Replace with your channel ID
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Set your small icon here
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Show the notification
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(notificationId, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    private fun createNotificationChannel() {
        // Create a notification channel
        val channelId = "my_channel_id" // Replace with your channel ID
        val channelName = "My Channel"
        val importance = NotificationManagerCompat.IMPORTANCE_HIGH

        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationChannel.description = "My Notification Channel Description"

        // Register the channel with the system
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    companion object {
        private const val TAG = "MyFirebaseMessagingService"
        private const val notificationId = 123 // Replace with your desired notification ID
    }
}
