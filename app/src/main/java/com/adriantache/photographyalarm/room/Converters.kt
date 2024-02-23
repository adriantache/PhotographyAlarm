package com.adriantache.photographyalarm.room

import androidx.room.TypeConverter
import com.adriantache.photographyalarm.model.WeatherInfoPoint
import org.jetbrains.annotations.VisibleForTesting
import java.time.LocalDateTime
import java.time.ZoneOffset

private const val DELIMITER = "-"
private const val LIST_DELIMITER = "|"

object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }


    @TypeConverter
    fun infoPointsToString(weatherInfoPointList: List<WeatherInfoPoint>?): String? {
        if (weatherInfoPointList == null) return null

        return weatherInfoPointList.joinToString(LIST_DELIMITER) { weatherInfoPoint ->
            weatherInfoPoint.id.toString() + DELIMITER + weatherInfoPoint.main + DELIMITER + weatherInfoPoint.description + DELIMITER + weatherInfoPoint.iconUrl
        }
    }

    @TypeConverter
    fun fromString(value: String?): List<WeatherInfoPoint>? {
        if (value == null) return null

        return value.split(LIST_DELIMITER).mapNotNull {
            fromStringPoint(it)
        }
    }

    @VisibleForTesting
    internal fun fromStringPoint(value: String?): WeatherInfoPoint? {
        if (value == null) return null

        val (id, main, description, iconUrl) = value.split(DELIMITER)

        return WeatherInfoPoint(
            id = id.toInt(),
            main = main,
            description = description,
            iconUrl = iconUrl,
        )
    }
}
