package com.adriantache.photographyalarm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "sunrise_table")
data class SunriseInfo(
    @PrimaryKey
    val sunrise: LocalDateTime?,
    val sunset: LocalDateTime?,
    val firstLight: LocalDateTime?,
    val lastLight: LocalDateTime?,
    val dawn: LocalDateTime?,
    val dusk: LocalDateTime?,
    val solarNoon: LocalDateTime?,
    val dayLength: String,
)
