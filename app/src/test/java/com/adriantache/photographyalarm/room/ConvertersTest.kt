package com.adriantache.photographyalarm.room

import com.adriantache.photographyalarm.model.WeatherInfoPoint
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate


class ConvertersTest {
    private val date = LocalDate.of(2024, 2, 21).atStartOfDay()
    private val timestamp = 1708473600L

    private val infoPoints = listOf(
        WeatherInfoPoint(123, "Bla", "Blablabla"),
        WeatherInfoPoint(122, "Bladasd", "sdasda"),
        WeatherInfoPoint(1, "sadasda", "fgedfgweaf"),
    )
    private val infoPointsString = "123-Bla-Blablabla|122-Bladasd-sdasda|1-sadasda-fgedfgweaf"
    private val firstInfoPointString = "123-Bla-Blablabla"

    @Test
    fun fromTimestamp() {
        val result = Converters.fromTimestamp(timestamp)

        assertThat(result).isEqualTo(date)
    }

    @Test
    fun dateToTimestamp() {
        val result = Converters.dateToTimestamp(date)

        assertThat(result).isEqualTo(timestamp)
    }

    @Test
    fun infoPointsToString() {
        val result = Converters.infoPointsToString(infoPoints)

        assertThat(result).isEqualTo(infoPointsString)
    }

    @Test
    fun fromString() {
        val result = Converters.fromString(infoPointsString)

        assertThat(result).isEqualTo(infoPoints)
    }

    @Test
    fun fromStringPoint() {
        val result = Converters.fromStringPoint(firstInfoPointString)

        assertThat(result).isEqualTo(infoPoints.first())
    }
}
