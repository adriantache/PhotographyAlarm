package com.adriantache.photographyalarm.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.model.ResultData
import com.adriantache.photographyalarm.ui.view.SetAlarmView
import com.adriantache.photographyalarm.ui.view.SunriseToggle
import com.adriantache.photographyalarm.ui.view.SunriseView
import com.adriantache.photographyalarm.ui.view.SunsetView
import com.adriantache.photographyalarm.ui.view.TimelineView
import com.adriantache.photographyalarm.ui.view.WeatherView

@Composable
fun SuccessScreen(
    data: ResultData,
    onSetAlarm: () -> Unit,
    onSwitchSunrise: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        SunriseToggle(
            isSunrise = data.isSunrise,
            onSwitchSunrise = onSwitchSunrise
        )

        Spacer(Modifier.height(8.dp))

        AnimatedContent(
            targetState = data,
            label = "",
            transitionSpec = {
                slideIn(initialOffset = {
                    IntOffset(x = 0, y = -it.height)
                }).togetherWith(
                    fadeOut() + slideOut(targetOffset = { IntOffset(x = 0, y = it.height) })
                )
            },
        ) { data ->
            Column {
                if (data.isSunrise) {
                    SunriseView(data.sunrise)
                } else {
                    SunsetView(data.sunset)
                }

                Spacer(Modifier.height(8.dp))

                WeatherView(data.weather)

                Spacer(Modifier.height(8.dp))

                TimelineView(data)

                Spacer(Modifier.height(8.dp))

                SetAlarmView(
                    weatherSummary = data.weatherSummary,
                    shouldSetAlarm = data.shouldSetAlarm,
                    alarmTime = data.alarmTime,
                    onSetAlarm = onSetAlarm
                )
            }
        }
    }
}
