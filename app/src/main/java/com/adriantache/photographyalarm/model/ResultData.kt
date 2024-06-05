package com.adriantache.photographyalarm.model

import com.adriantache.photographyalarm.R
import com.adriantache.photographyalarm.data.LocalDateTimeAsLongSerializer
import com.adriantache.photographyalarm.data.LocalTimeAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.absoluteValue

@Serializable
data class ResultData(
    val isSunrise: Boolean,
    val sunrise: SunriseResultSummary,
    val sunset: SunsetResultSummary,
    @Serializable(LocalDateTimeAsLongSerializer::class)
    val retrievedAt: LocalDateTime = LocalDateTime.now(),
) {
    val weather = if (isSunrise) sunrise.weather else sunset.weather

    val shouldSetAlarm = weather.shouldSetAlarm

    @Serializable(LocalTimeAsLongSerializer::class)
    val alarmTime: LocalTime = run {
        val datapoint = if (isSunrise) sunrise.sunrise else sunset.sunset

        datapoint.timestamp.minusMinutes(30)
    }

    @Serializable(LocalTimeAsLongSerializer::class)
    val minTime: LocalTime = if (isSunrise) sunrise.minTime else sunset.minTime

    @Serializable(LocalTimeAsLongSerializer::class)
    val maxTime: LocalTime = if (isSunrise) sunrise.maxTime else sunset.maxTime

    private val timeDifference = maxTime.toNanoOfDay() - minTime.toNanoOfDay()

    @Serializable(LocalTimeAsLongSerializer::class)
    val midPoint: LocalTime = LocalTime.ofNanoOfDay(minTime.toNanoOfDay() + timeDifference / 2)

    fun getPercent(reference: LocalTime): Double {
        return (reference.toNanoOfDay() - minTime.toNanoOfDay()).toDouble() / timeDifference
    }

    val weatherSummary: String = run {
        val prefix = "The weather is expected to be ${weather.closest.description} at ${weather.closest.time}."
        val suffix = if (shouldSetAlarm) {
            "To set an alarm, click the button below."
        } else {
            "If you still want to set an alarm, click the button below."
        }

        "$prefix $suffix"
    }

    companion object {
        fun process(
            isSunrise: Boolean,
            sunriseData: SunriseInfo,
            weatherPoints: List<WeatherInfo>
        ): ResultData {
            return ResultData(
                isSunrise = isSunrise,
                sunrise = SunriseResultSummary(
                    firstLight = sunriseData.firstLight?.let { SunriseResultData(time = it, iconRes = R.drawable.noun_sunrise_6475878) },
                    dawn = sunriseData.dawn?.let { SunriseResultData(time = it, iconRes = R.drawable.noun_dawn_6475869) },
                    sunrise = SunriseResultData(time = sunriseData.sunrise, iconRes = R.drawable.noun_sun_6475868),
                    weather = getWeather(sunriseData.sunrise, weatherPoints),
                ),
                sunset = SunsetResultSummary(
                    sunset = SunriseResultData(time = sunriseData.sunset, iconRes = R.drawable.noun_sun_6475868),
                    dusk = sunriseData.dusk?.let { SunriseResultData(time = it, iconRes = R.drawable.noun_dusk_6475877) },
                    lastLight = sunriseData.lastLight?.let { SunriseResultData(time = it, iconRes = R.drawable.noun_sunrise_6475878) },
                    weather = getWeather(sunriseData.sunset, weatherPoints),
                ),
            )
        }

        private fun getWeather(
            target: LocalDateTime,
            weatherPoints: List<WeatherInfo>
        ): WeatherResultSummary {
            val targetWeather = weatherPoints.minBy { Duration.between(target, it.date).toMillis().absoluteValue }

            val targetWeatherIndex = weatherPoints.indexOf(targetWeather)
            val firstWeatherIndex = (targetWeatherIndex - 1).coerceAtLeast(0)
            val firstWeather = weatherPoints[firstWeatherIndex]

            val lastWeatherIndex = (targetWeatherIndex + 1).coerceAtMost(weatherPoints.size - 1)
            val lastWeather = weatherPoints[lastWeatherIndex]

            return WeatherResultSummary(
                before = WeatherResultData(
                    description = firstWeather.ids.first().description,
                    ids = firstWeather.ids.map { it.id },
                    iconUrl = firstWeather.ids.first().iconUrl,
                    time = firstWeather.date
                ),
                closest = WeatherResultData(
                    description = targetWeather.ids.first().description,
                    ids = targetWeather.ids.map { it.id },
                    iconUrl = targetWeather.ids.first().iconUrl,
                    time = targetWeather.date
                ),
                after = WeatherResultData(
                    description = lastWeather.ids.first().description,
                    ids = lastWeather.ids.map { it.id },
                    iconUrl = lastWeather.ids.first().iconUrl,
                    time = lastWeather.date
                ),
            )
        }
    }
}
