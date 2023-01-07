package com.example.dlp.FCM_Service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.AudioAttributes
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.dlp.R
import com.example.dlp.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcastManager: LocalBroadcastManager? = null
    override fun onCreate() {
        broadcastManager = LocalBroadcastManager.getInstance(this)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    fun showDataMessage(msgTitle: String?, msgContent: String?) {
        Log.i("### data msgTitle : ", msgTitle!!)
        Log.i("### data msgContent : ", msgContent!!)
        val toastText = String.format("[Data 메시지] title: %s => content: %s", msgTitle, msgContent)
        Looper.prepare()
        Toast.makeText(applicationContext, toastText, Toast.LENGTH_LONG).show()
        Looper.loop()
    }

    /**
     * 수신받은 메시지를 Toast로 보여줌
     * @param msgTitle
     * @param msgContent
     */
    fun showNotificationMessage(msgTitle: String?, msgContent: String?) {
        Log.i("### noti msgTitle : ", msgTitle!!)
        Log.i("### noti msgContent : ", msgContent!!)
        val toastText =
            String.format("[Notification 메시지] title: %s => content: %s", msgTitle, msgContent)
        Looper.prepare()
        Toast.makeText(applicationContext, toastText, Toast.LENGTH_LONG).show()
        Looper.loop()
    }

    /**
     * 메시지 수신받는 메소드
     * @param remoteMessage
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["body"]
        val channel = remoteMessage.data["channel_id"]
        Log.e("//=======//", "===================================//")
        Log.e("   Title  :", title!!)
        Log.e("//=======//", "===================================//")
        Log.e("//=======//", "===================================//")
        Log.e("   body   :", message!!)
        Log.e("//=======//", "===================================//")
        Log.e("//=======//", "===================================//")
        Log.e("   channel   :", channel!!)
        Log.e("//=======//", "===================================//")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_DESCRIPTION = "ChannerDescription"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(message)
                )
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                builder.setContentTitle(title)
                builder.setVibrate(longArrayOf(500, 500))
            }
            //        notificationManager.notify(Integer.parseInt(remoteMessage.getData().get("Type")), builder.build());
            notificationManager.notify(System.currentTimeMillis().toInt() / 1000, builder.build())
        }

        fun showNotification(title: String, message: String) {
            //팝업 터치시 이동할 액티비티를 지정합니다.
            val intent = Intent(this, SplashActivity::class.java)
            //알림 채널 아이디 : 본인 하고싶으신대로...
            val channel_id = "CHN_ID"
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            //기본 사운드로 알림음 설정. 커스텀하려면 소리 파일의 uri 입력
            var builder: NotificationCompat.Builder =
                NotificationCompat.Builder(applicationContext, channel_id)
                    .setSmallIcon(R.drawable.dlp_icon3)
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(1000, 1000, 1000)) //알림시 진동 설정 : 1초 진동, 1초 쉬고, 1초 진동
                    .setOnlyAlertOnce(true) //동일한 알림은 한번만.. : 확인 하면 다시 울림
                    .setContentIntent(pendingIntent)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) { //안드로이드 버전이 커스텀 알림을 불러올 수 있는 버전이면
                //커스텀 레이아웃 호출
            } else { //아니면 기본 레이아웃 호출
                builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.dlp_icon3) //커스텀 레이아웃에 사용된 로고 파일과 동일하게..
            }
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //알림 채널이 필요한 안드로이드 버전을 위한 코드
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel =
                    NotificationChannel(channel_id, "CHN_NAME", NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(notificationChannel)
            }
            //알림 표시 !
            notificationManager.notify(0, builder.build())
        }
    }
}