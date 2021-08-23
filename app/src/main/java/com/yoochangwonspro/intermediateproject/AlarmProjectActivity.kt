package com.yoochangwonspro.intermediateproject

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*

class AlarmProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_project)

        initOnOffButton()
        initChangeAlarmTimeButton()

        val model = fetchDataFromSharedPreferences()
        renderView(model)
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
                renderView(model)

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

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putString(ALARM_KEY, model.makeDataForDB())
            putBoolean(ONOFF_KEY, model.onOff)
            commit()
        }

        return model
    }

    private fun fetchDataFromSharedPreferences(): AlarmDisplayModel {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val timeDbValue = sharedPreferences.getString(ALARM_KEY, "9:30") ?: "9:30"
        val onOffDbValue = sharedPreferences.getBoolean(ONOFF_KEY, false)
        val alarmData = timeDbValue.split(":")

        val alarmModel = AlarmDisplayModel(
            hour = alarmData[0].toInt(),
            minute = alarmData[1].toInt(),
            onOffDbValue
        )

        // 보정 예외 처리
//        val pendingIntent = PendingIntent.getBroadcast(
//            this,
//            ALARM_REQUEST_CODE,
//            Intent(this, AlarmReceiver::class.java),
//            PendingIntent.FLAG_NO_CREATE
//        )
//
//        if ((pendingIntent == null) && alarmModel.onOff) {
//            // 알람은 꺼져있는데, 데이터는 있는 경우
//            alarmModel.onOff = false
//
//        } else if ((pendingIntent != null) and alarmModel.onOff.not()) {
//            // 알람은 등록이 되있는데, 데이터가 없는 경우
//            pendingIntent.cancel()
//        }

        return alarmModel
    }

    private fun renderView(model: AlarmDisplayModel) {
        findViewById<TextView>(R.id.alram_am_pm_text_view).apply {
            text = model.ampmText
        }

        findViewById<TextView>(R.id.alram_time_text_view).apply {
            text = model.timeText
        }

        findViewById<Button>(R.id.alram_on_off_button).apply {
            text = model.onOffText
            tag = model
        }
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "time"
        private const val ALARM_KEY = "alarm"
        private const val ONOFF_KEY = "onOff"
        private const val ALARM_REQUEST_CODE = 1000
    }
}