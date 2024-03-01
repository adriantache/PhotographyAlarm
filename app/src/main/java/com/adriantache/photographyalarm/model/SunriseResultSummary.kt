package com.adriantache.photographyalarm.model

import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
data class SunriseResultSummary(
    val firstLight: SunriseResultData,
    val dawn: SunriseResultData,
    val sunrise: SunriseResultData
) {
    private val list = listOf(firstLight, dawn, sunrise)

    @Serializable(LocalTimeAsLongSerializer::class)
    val minTime: LocalTime = list.minOf { it.timestamp }

    @Serializable(LocalTimeAsLongSerializer::class)
    val maxTime: LocalTime = list.minOf { it.timestamp }
}