package com.adriantache.photographyalarm.data

import android.content.Context
import android.location.Location
import android.widget.Toast
import com.adriantache.photographyalarm.data.api.ApiCalls
import com.adriantache.photographyalarm.data.cache.Preferences
import com.adriantache.photographyalarm.model.ResultData
import java.time.ZoneOffset

private const val CACHE_EXPIRATION = 60 * 60 * 6 // 6 hours

class Repository(
    private val context: Context,
    private val apiCalls: ApiCalls = ApiCalls(),
    private val preferences: Preferences = Preferences(context),
) {
    suspend fun getData(
        location: Location,
        isSunrise: Boolean
    ): ResultData? {
        val cache = preferences.getCache()
            ?.takeIf { System.currentTimeMillis() - it.retrievedAt.toInstant(ZoneOffset.UTC).toEpochMilli() <= CACHE_EXPIRATION }

        if (cache != null) return cache.copy(isSunrise = isSunrise)

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

        val data = ResultData.process(
            isSunrise = isSunrise,
            sunriseData = sunriseData.getOrThrow()!!,
            weatherPoints = weatherData.getOrThrow()!!,
        )

        preferences.saveCache(data)

        return data
    }
}

