package com.yoochangwonspro.intermediateproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

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
    }
}