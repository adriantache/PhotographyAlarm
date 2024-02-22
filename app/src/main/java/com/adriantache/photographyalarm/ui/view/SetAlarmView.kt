package com.adriantache.photographyalarm.ui.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.time.LocalTime

@Composable
fun SetAlarmView(
    shouldSetAlarm: Boolean,
    alarmTime: LocalTime?,
    onSetAlarm: () -> Unit,
) {
    if (shouldSetAlarm) {
        Button(onClick = { onSetAlarm() }) {
            Text("Set alarm for tomorrow morning at $alarmTime!")
        }
    }
}
