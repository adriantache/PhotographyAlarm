package com.adriantache.photographyalarm.model

import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalTime

@Serializable
data class SunriseResultSummary(
    val firstLight: SunriseResultData?,
    val dawn: SunriseResultData?,
    val sunrise: SunriseResultData,
    val weather: WeatherResultSummary,
) {
    @Transient
    private val list = listOfNotNull(firstLight, dawn, sunrise).map { it.timestamp } +
            listOf(weather.before, weather.closest, weather.after).map { it.timestamp }

    @Serializable(LocalTimeAsLongSerializer::class)
    val minTime: LocalTime = list.min()

    @Serializable(LocalTimeAsLongSerializer::class)
    val maxTime: LocalTime = list.max()
}