package com.adriantache.photographyalarm.logic

import java.time.LocalTime

data class ResultData(
    val sunrise: String,
    val weather: String,
    val shouldSetAlarm: Boolean,
    val alarmTime: LocalTime? = null,
)
