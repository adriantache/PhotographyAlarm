package com.adriantache.photographyalarm.model

import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
data class WeatherResultSummary(
    val before: WeatherResultData,
    val closest: WeatherResultData,
    val after: WeatherResultData,
) {
    private val list = listOf(before, closest, after)

    @Serializable(LocalTimeAsLongSerializer::class)
    val minTime: LocalTime = list.minOf { it.timestamp }

    @Serializable(LocalTimeAsLongSerializer::class)
    val maxTime: LocalTime = list.minOf { it.timestamp }

    val shouldSetAlarm = closest.ids.any { it in 800..802 }
}
