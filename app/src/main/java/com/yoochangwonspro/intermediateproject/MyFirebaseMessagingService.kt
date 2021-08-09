package com.yoochangwonspro.intermediateproject

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
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }
}
