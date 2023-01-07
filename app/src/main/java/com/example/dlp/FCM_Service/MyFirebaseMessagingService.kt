package com.example.dlp.FCM_Service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dlp.MainActivity
import com.example.dlp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService(){

    // 앱 재설치/데이터소거/새로운 기기에서 앱 복원 시 Token은 재생성됨
    // 이를 해결 하기 위해 서버에 Token을 자동으로 갱신 시켜줘야함
    // 그걸 처리하는 곳이 onNewToken
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val title = remoteMessage.data["message"]
        val message = remoteMessage.data["score"]


        NotificationManagerCompat.from(this)
            .notify(0, createNotification(title, message))
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification(title: String?,
                                   message: String?
    ): Notification {
        val intent = Intent(this, MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        intent.putExtra("fromFCM_Channel", message)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.dlp_icon3)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return notificationBuilder.build()
    }
    companion object{
        private const val CHANNEL_NAME = "점선면"
        private const val CHANNEL_DESCRIPTION = "점선면 투표시작"
        private const val CHANNEL_ID = "Channel Id"
    }
}