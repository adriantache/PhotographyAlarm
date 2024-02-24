package com.adriantache.photographyalarm.data.api

import android.location.Location
import android.util.Log
import com.adriantache.photographyalarm.data.api.sunrise.SunriseApi
import com.adriantache.photographyalarm.data.api.weather.WeatherApi
import com.adriantache.photographyalarm.model.SunriseInfo
import com.adriantache.photographyalarm.model.WeatherInfo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_DATE

class ApiCalls {
    private val okHttpClient: OkHttpClient = getOkHttpClient()
    private val retrofit: Retrofit = getRetrofit()

    // TODO: get from database / save to database
    suspend fun getSunriseData(location: Location): Result<SunriseInfo?> {
        val sunriseService = retrofit.create<SunriseApi>()

        val tomorrow = LocalDate.now().plusDays(1)
        val dateFormatter = ISO_DATE
        val date = dateFormatter.format(tomorrow)

        return wrapResponse {
            sunriseService.getSunriseData(
                latitude = location.latitude,
                longitude = location.longitude,
                date = date,
            )
        }.map {
            it?.toInfo(tomorrow)
        }
    }

    // TODO: get from database / save to database
    suspend fun getWeatherData(location: Location): Result<List<WeatherInfo>?> {
        val weatherService = retrofit.create<WeatherApi>()

        // TODO: call this API sparingly, cache response and only call again if previous forecast expired
        return wrapResponse {
            weatherService.getWeatherForecast(
                latitude = location.latitude,
                longitude = location.longitude,
            )
        }.map { it?.getFormattedList() }
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
        }

        return Retrofit.Builder()
            .baseUrl("http://localhost/") // unused.
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    private suspend fun <T> wrapResponse(call: suspend () -> Response<T>): Result<T?> {
        return runCatching {
            val response = call()

            if (response.isSuccessful) {
                response.body()
            } else {
                error(response.errorBody().toString())
            }
        }.onFailure {
            Log.e(this::class.java.simpleName, "Api Call error:", it)
        }
    }
}
