package com.adriantache.photographyalarm.model

import java.time.LocalDateTime

data class SunriseInfo(
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val firstLight: LocalDateTime?,
    val lastLight: LocalDateTime?,
    val dawn: LocalDateTime?,
    val dusk: LocalDateTime?,
    val solarNoon: LocalDateTime?,
    val dayLength: String?,
)
