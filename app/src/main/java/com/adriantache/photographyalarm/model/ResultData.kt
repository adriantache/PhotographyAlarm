package com.adriantache.photographyalarm.model

import com.adriantache.photographyalarm.data.LocalDateTimeAsLongSerializer
import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Serializable
data class ResultData(
    val sunrise: List<SunriseResultData>,
    val weather: List<WeatherResultData>,
    @Serializable(LocalDateTimeAsLongSerializer::class)
    val retrievedAt: LocalDateTime = LocalDateTime.now(),
) {
    val shouldSetAlarm = weather[1].ids.any { it in 800..802 }

    @Serializable(LocalTimeAsLongSerializer::class)
    val alarmTime: LocalTime = sunrise[2].timestamp.minusMinutes(30)

    @Serializable(LocalTimeAsLongSerializer::class)
    val minTime: LocalTime = (sunrise.map { it.timestamp } + weather.map { it.timestamp })
        .minBy { it.toNanoOfDay() }
        .truncatedTo(ChronoUnit.MINUTES)

    @Serializable(LocalTimeAsLongSerializer::class)
    val maxTime: LocalTime = (sunrise.map { it.timestamp } + weather.map { it.timestamp })
        .maxBy { it.toNanoOfDay() }
        .truncatedTo(ChronoUnit.MINUTES)

    private val timeDifference = maxTime.toNanoOfDay() - minTime.toNanoOfDay()

    @Serializable(LocalTimeAsLongSerializer::class)
    val midPoint: LocalTime = LocalTime.ofNanoOfDay(minTime.toNanoOfDay() + timeDifference / 2)

    fun getPercent(reference: LocalTime): Double {
        return (reference.toNanoOfDay() - minTime.toNanoOfDay()).toDouble() / timeDifference
    }
}
