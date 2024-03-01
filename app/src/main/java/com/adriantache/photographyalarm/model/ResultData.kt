package com.adriantache.photographyalarm.model

import com.adriantache.photographyalarm.data.LocalDateTimeAsLongSerializer
import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Serializable
data class ResultData(
    val sunrise: SunriseResultSummary,
    val weather: WeatherResultSummary,
    @Serializable(LocalDateTimeAsLongSerializer::class)
    val retrievedAt: LocalDateTime = LocalDateTime.now(),
) {
    val shouldSetAlarm = weather.shouldSetAlarm

    @Serializable(LocalTimeAsLongSerializer::class)
    val alarmTime: LocalTime = sunrise.sunrise.timestamp.minusMinutes(30)

    @Serializable(LocalTimeAsLongSerializer::class)
    val minTime: LocalTime = listOf(sunrise.minTime, weather.minTime)
        .minBy { it.toNanoOfDay() }
        .truncatedTo(ChronoUnit.MINUTES)

    @Serializable(LocalTimeAsLongSerializer::class)
    val maxTime: LocalTime = listOf(sunrise.maxTime, weather.maxTime)
        .maxBy { it.toNanoOfDay() }
        .truncatedTo(ChronoUnit.MINUTES)

    private val timeDifference = maxTime.toNanoOfDay() - minTime.toNanoOfDay()

    @Serializable(LocalTimeAsLongSerializer::class)
    val midPoint: LocalTime = LocalTime.ofNanoOfDay(minTime.toNanoOfDay() + timeDifference / 2)

    fun getPercent(reference: LocalTime): Double {
        return (reference.toNanoOfDay() - minTime.toNanoOfDay()).toDouble() / timeDifference
    }

    fun getWeatherSummary(): String {
        val prefix = "The weather is expected to be ${weather.closest.description} at ${weather.closest.time}."
        val suffix = if (shouldSetAlarm) {
            "To set an alarm, click the button below."
        } else {
            "If you still want to set an alarm, click the button below."
        }

        return "$prefix $suffix"
    }
}
