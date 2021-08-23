package com.yoochangwonspro.intermediateproject

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class AlarmProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_project)

        // step0 뷰를 초기화해주기
        initOnOffButton()
        initChangeAlarmTimeButton()

        // step1 데이터 가져오기

        // step2 뷰에 데이터를 그려주기
    }

    private fun initOnOffButton() {
        val onOffButton = findViewById<Button>(R.id.alram_on_off_button)
        onOffButton.setOnClickListener {
            // 데이터를 확인을 한다.

            // onOff 에 따라 작업을 처리한다.

            // 오프 -> 알람을 제거
            // 온 -> 알람을 등록

            // 데이터를 저장한다.
        }
    }

    private fun initChangeAlarmTimeButton() {
        val changeAlarmButton = findViewById<Button>(R.id.alram_change_time_button)
        changeAlarmButton.setOnClickListener {

            val calendar = Calendar.getInstance()

            TimePickerDialog(this, { picker, hour, minute ->

                val model = saveAlarmModel(hour, minute, false)
                // 데이터를 저장한다.
                // 뷰를 업데이트 한다.
                // 기존에 있던 알람을 삭제한다.
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
                .show()
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveAlarmModel(
        hour: Int,
        minute: Int,
        onOff: Boolean,
    ): AlarmDisplayModel {
        val model = AlarmDisplayModel(
            hour = hour,
            minute = minute,
            onOff = false
        )

        val sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putString("alarm", model.makeDataForDB())
            putBoolean("onOff", model.onOff)
            commit()
        }

        return model
    }
}