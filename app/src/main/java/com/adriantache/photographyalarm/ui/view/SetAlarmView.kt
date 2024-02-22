package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalTime

@Composable
fun SetAlarmView(
    shouldSetAlarm: Boolean,
    alarmTime: LocalTime?,
    onSetAlarm: () -> Unit,
) {
    if (shouldSetAlarm) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onSetAlarm() }) {
                Text("Set alarm for tomorrow morning at ${alarmTime?.hour}:${alarmTime?.minute}")
            }
        }
    }
}

@Preview
@Composable
private fun SetAlarmViewPreview() {
    SetAlarmView(
        shouldSetAlarm = true,
        alarmTime = LocalTime.now(),
        onSetAlarm = {}
    )
}
