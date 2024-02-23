package com.adriantache.photographyalarm.logic

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

data class WeatherResultData(
    val iconUrl: String,
    val time: String,
    val timestamp: LocalTime,
) {
    constructor(iconUrl: String, time: LocalDateTime) : this(
        iconUrl = iconUrl,
        timestamp = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES),
        time = time.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString(),
    )
}
