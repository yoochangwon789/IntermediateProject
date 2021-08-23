package com.yoochangwonspro.intermediateproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AlramProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alram_project)

        // step0 뷰를 초기화해주기
        initOnOffButton()
        initChangeAlarmTimeButton()

        // step1 데이터 가져오기

        // step2 뷰에 데이터를 그려주기
    }

    private fun initChangeAlarmTimeButton() {
    }

    private fun initOnOffButton() {
    }
}