package com.yoochangwonspro.intermediateproject

import android.annotation.SuppressLint
import android.app.AlarmManager
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
            // as? => 형변환의 실패 했을 때 null 로 떨어지게 된다.
            // 데이터를 확인을 한다.
            val model = it.tag as? AlarmDisplayModel ?: return@setOnClickListener
            val newModel = saveAlarmModel(model.hour, model.minute, model.onOff.not())
            renderView(newModel)

            // onOff 에 따라 작업을 처리한다.
            if (newModel.onOff) {
                // 켜진 경우 -> 알람을 등록
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, newModel.hour)
                    set(Calendar.MINUTE, newModel.minute)

                    // 지나간 시간의 관한 알람 조건
                    // add 함수를 통해서 하루 밀어주는 방식으로 설정
                    if (before(Calendar.getInstance())) {
                        add(Calendar.DATE , 1)
                    }
                }

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
                )

                alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )

            } else {
                // 꺼진 경우 -> 알람을 제거
                cancelAlarm()
            }
        }
    }

    private fun initChangeAlarmTimeButton() {
        val changeAlarmButton = findViewById<Button>(R.id.alram_change_time_button)
        changeAlarmButton.setOnClickListener {

            val calendar = Calendar.getInstance()

            TimePickerDialog(this, { picker, hour, minute ->

                val model = saveAlarmModel(hour, minute, false)
                renderView(model)

                // 기존의 알람을 삭제하는 부분
                cancelAlarm()

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
            onOff = onOff
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
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        )

        if ((pendingIntent == null) && alarmModel.onOff) {
            // 알람은 꺼져있는데, 데이터는 있는 경우
            alarmModel.onOff = false

        } else if ((pendingIntent != null) and alarmModel.onOff.not()) {
            // 알람은 등록이 되있는데, 데이터가 없는 경우
            pendingIntent.cancel()
        }

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

    private fun cancelAlarm() {
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        )
        pendingIntent?.cancel()
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "time"
        private const val ALARM_KEY = "alarm"
        private const val ONOFF_KEY = "onOff"
        private const val ALARM_REQUEST_CODE = 1000
    }
}