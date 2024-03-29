package com.adriantache.photographyalarm.model

import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Serializable
data class WeatherResultData(
    val description: String,
    val iconUrl: String,
    val ids: List<Int>,
    val time: String,
    @Serializable(LocalTimeAsLongSerializer::class) val timestamp: LocalTime,
) {
    constructor(
        description: String,
        iconUrl: String,
        time: LocalDateTime,
        ids: List<Int>,
    ) : this(
        description = description,
        iconUrl = iconUrl,
        timestamp = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES),
        time = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString(),
        ids = ids,
    )
}
