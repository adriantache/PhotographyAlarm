package com.adriantache.photographyalarm.model

import java.time.LocalDateTime

data class WeatherInfo(
    val date: LocalDateTime,
    val ids: List<WeatherInfoPoint>,
)
