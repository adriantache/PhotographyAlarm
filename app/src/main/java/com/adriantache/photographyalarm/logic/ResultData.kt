package com.adriantache.photographyalarm.logic

import java.time.LocalTime
import java.time.temporal.ChronoUnit

data class ResultData(
    val sunrise: List<SunriseResultData>,
    val weather: List<WeatherResultData>,
    val shouldSetAlarm: Boolean,
    val alarmTime: LocalTime? = null,
) {
    val minTime: LocalTime =
        (sunrise.map { it.timestamp } + weather.map { it.timestamp }).minBy { it.toNanoOfDay() }.truncatedTo(ChronoUnit.MINUTES)
    val maxTime: LocalTime =
        (sunrise.map { it.timestamp } + weather.map { it.timestamp }).maxBy { it.toNanoOfDay() }.truncatedTo(ChronoUnit.MINUTES)

    private val timeDifference = maxTime.toNanoOfDay() - minTime.toNanoOfDay()

    val midPoint: LocalTime = LocalTime.ofNanoOfDay(minTime.toNanoOfDay() + timeDifference / 2)

    fun getPercent(reference: LocalTime): Double {
        return (reference.toNanoOfDay() - minTime.toNanoOfDay()).toDouble() / timeDifference
    }
}
