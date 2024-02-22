package com.adriantache.photographyalarm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.logic.ResultData
import com.adriantache.photographyalarm.ui.view.SetAlarmView
import com.adriantache.photographyalarm.ui.view.SunriseView
import com.adriantache.photographyalarm.ui.view.WeatherView

@Composable
fun SuccessScreen(
    data: ResultData,
    onSetAlarm: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        SunriseView(data.sunrise)

        WeatherView(data.weather)

        Spacer(Modifier.weight(1f))

        SetAlarmView(
            shouldSetAlarm = data.shouldSetAlarm,
            alarmTime = data.alarmTime,
            onSetAlarm = onSetAlarm
        )
    }
}
