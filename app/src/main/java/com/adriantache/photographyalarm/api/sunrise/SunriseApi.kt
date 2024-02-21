package com.adriantache.photographyalarm.api.sunrise

import com.adriantache.photographyalarm.api.sunrise.model.SunriseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val SUNRISE_API = "https://api.sunrisesunset.io/json?lat=38.907192&lng=-77.036873"

@Suppress("kotlin:S6517")
interface SunriseApi {
    @GET(SUNRISE_API)
    suspend fun getSunriseData(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("date") date: String,
        @Query("timezone") timezone: String = "UTC",
    ): Response<SunriseResponse>
}
