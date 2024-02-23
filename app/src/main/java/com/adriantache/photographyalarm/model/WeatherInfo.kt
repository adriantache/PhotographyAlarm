package com.adriantache.photographyalarm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "weather_table")
data class WeatherInfo(
    @PrimaryKey
    val date: LocalDateTime,
    val ids: List<WeatherInfoPoint>,
)

@Entity(tableName = "weather_data_table")
data class WeatherInfoPoint(
    @PrimaryKey
    val id: Int,
    val main: String,
    val description: String,
    val iconUrl: String,
)
