package com.yoochangwonspro.intermediateproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

class PushAlarmActivity : AppCompatActivity() {

    private val resultTextView: TextView by lazy {
        findViewById(R.id.push_alarm_result_text_view)
    }

    private val firebaseToken: TextView by lazy {
        findViewById(R.id.push_alarm_firebase_token_text_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_alarm)

        initFirebase()
        updateResult()
    }

    private fun initFirebase() {
        // 파이어 베이스 토큰 가져오는 함수
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseToken.text = task.result
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)
        updateResult(true)
    }

    // isNewIntent 으로 인해서 앱이 실행되었는가
    // 기존의 켜져있었는데 notification 을 눌러서 앱이 갱신되었는가
    @SuppressLint("SetTextI18n")
    private fun updateResult(isNewIntent: Boolean = false) {
        resultTextView.text = (intent.getStringExtra("notificationType") ?: "앱 런처") +
                if (isNewIntent) {
                    "(으)로 갱신했습니다."
                } else {
                    "(으)로 실행했습니다."
                }
    }
}