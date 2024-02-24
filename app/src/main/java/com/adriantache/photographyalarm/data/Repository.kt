package com.adriantache.photographyalarm.data

import android.content.Context
import android.location.Location
import android.widget.Toast
import com.adriantache.photographyalarm.R
import com.adriantache.photographyalarm.data.api.ApiCalls
import com.adriantache.photographyalarm.data.cache.Preferences
import com.adriantache.photographyalarm.model.ResultData
import com.adriantache.photographyalarm.model.SunriseResultData
import com.adriantache.photographyalarm.model.WeatherResultData
import java.time.Duration
import java.time.ZoneOffset
import kotlin.math.absoluteValue

private const val CACHE_EXPIRATION = 60 * 60 * 6 // 6 hours

class Repository(
    private val context: Context,
    private val apiCalls: ApiCalls = ApiCalls(),
    private val preferences: Preferences = Preferences(context),
) {
    suspend fun getData(location: Location): ResultData? {
        val cache = preferences.getCache()
            ?.takeIf { System.currentTimeMillis() - it.retrievedAt.toInstant(ZoneOffset.UTC).toEpochMilli() <= CACHE_EXPIRATION }

        if (cache != null) return cache

        val sunriseData = apiCalls.getSunriseData(location)

        if (sunriseData.getOrNull()?.sunrise == null) {
            Toast.makeText(context, "Cannot get sunrise data.", Toast.LENGTH_SHORT).show()
            return null
        }

        val weatherData = apiCalls.getWeatherData(location)

        if (weatherData.getOrNull() == null) {
            Toast.makeText(context, "Cannot get weather data. $weatherData", Toast.LENGTH_SHORT).show()
            return null
        }

        val sunrise = sunriseData.getOrThrow()!!.sunrise
        val weatherPoints = weatherData.getOrThrow()!!
        val targetWeather = weatherPoints.minBy { Duration.between(sunrise, it.date).toMillis().absoluteValue }

        val weatherIndex = weatherPoints.indexOf(targetWeather)
        val firstWeatherIndex = (weatherIndex - 1).coerceAtLeast(0)
        val firstWeather = weatherPoints[firstWeatherIndex]

        val lastWeatherIndex = (weatherIndex + 1).coerceAtMost(weatherPoints.size - 1)
        val lastWeather = weatherPoints[lastWeatherIndex]

        val data = ResultData(
            sunrise = listOf(
                SunriseResultData(time = sunriseData.getOrThrow()!!.firstLight, iconRes = R.drawable.noun_sunrise_6475878),
                SunriseResultData(time = sunriseData.getOrThrow()!!.dawn, iconRes = R.drawable.noun_dawn_6475869),
                SunriseResultData(time = sunrise, iconRes = R.drawable.noun_sun_6475868),
            ),
            weather = listOf(
                WeatherResultData(
                    ids = firstWeather.ids.map { it.id },
                    iconUrl = firstWeather.ids.first().iconUrl,
                    time = firstWeather.date
                ),
                WeatherResultData(
                    ids = targetWeather.ids.map { it.id },
                    iconUrl = targetWeather.ids.first().iconUrl,
                    time = targetWeather.date
                ),
                WeatherResultData(
                    ids = lastWeather.ids.map { it.id },
                    iconUrl = lastWeather.ids.first().iconUrl,
                    time = lastWeather.date
                ),
            ),
        )

        preferences.saveCache(data)

        return data
    }
}

