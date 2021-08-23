package com.yoochangwonspro.intermediateproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
        val onOffButton = findViewById<Button>(R.id.alram_on_off_button)
        onOffButton.setOnClickListener {
            // 데이터를 확인을 한다.

            // onOff 에 따라 작업을 처리한다.

            // 오프 -> 알람을 제거
            // 온 -> 알람을 등록

            // 데이터를 저장한다.
        }
    }

    private fun initOnOffButton() {
        val changeAlramButton = findViewById<Button>(R.id.alram_change_time_button)
        changeAlramButton.setOnClickListener {
            // 현재시간을 일단 가져온다.
            // TimePickDialog 띄워워서 시간을 설정을 하도록 하게끔 하고, 그 시간을 가져와서

            // 데이터를 저장한다.
            // 뷰를 업데이트 한다.
            // 기존에 있던 알람을 삭제한다.
        }
    }
}