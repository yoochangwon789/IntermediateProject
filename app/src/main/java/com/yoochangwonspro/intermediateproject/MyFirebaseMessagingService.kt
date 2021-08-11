package com.yoochangwonspro.intermediateproject

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // 토큰은 굉장이 자주 변경이 될 수 있다
    // 그렇기 때문에 현재 토큰을 가져와서 사용하는 경우는 실제 라이브 서비스에서는 무리가 있을 수 있다.
    // 실제 라이브 서비스를 운영할때 onNewToken 이 호출 즉 새로운 토큰이 갱신될 때 마다
    // 서버에다가 해당 토큰을 갱신해줘야하는 처리를 해줘야한다
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    // firebase 에서 메세지를 발신하고 클라이언트 단에서 수신할 때 마다 이 메서드를 호출하게 된다
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        // type 의 값이 null 이 아니고 type 의 값이 NotificationType class 상수의 이름과 같은 것으로 매치
        val type = remoteMessage.data["type"]
            ?.let { NotificationType.valueOf(it) }

        // null 이면 알림을 생성하지 않는다
        type ?: return

        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]


        // NotificationCompat.Builder 를 실제로 notify 시키는 부분
        // 메세지를 보냈을 때 타이틀과 메세지를 맞춰서 알림을 보여주는 것을 확인할 수 있다.
        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type, title, message))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?,
    ): Notification {
        // 알림 컨텐츠 생성
        // 알림이 울리게 되면 statusBar 나 좌측 상단에 설정한 아이콘이 뜨게된다
        val notificationBuilder =  NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        when (type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {

            }
            NotificationType.CUSTOM -> {

            }
        }
    }

    companion object {
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Emoji Party를 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
    }
}
