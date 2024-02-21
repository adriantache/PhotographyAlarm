package com.adriantache.photographyalarm.api.weather

import com.adriantache.photographyalarm.BuildConfig
import com.adriantache.photographyalarm.api.weather.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/forecast"

@Suppress("kotlin:S6517")
interface WeatherApi {
    @GET(WEATHER_API_URL)
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric",
    ): Response<WeatherResponse>
}
