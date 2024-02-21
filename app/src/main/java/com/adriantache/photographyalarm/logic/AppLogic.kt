package com.adriantache.photographyalarm.logic

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.provider.AlarmClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.adriantache.photographyalarm.api.ApiCalls
import com.adriantache.photographyalarm.logic.AppState.Init
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Duration
import java.time.LocalTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.absoluteValue

class AppLogic(
    private val context: Context,
    private val onRequestPermissions: (Array<String>) -> Unit,
) {
    private val _statusFlow: MutableStateFlow<AppState> = MutableStateFlow(Init)
    val statusFlow: StateFlow<AppState> = _statusFlow

    private val _statusTextFlow: MutableStateFlow<String> = MutableStateFlow("[Init]")
    val statusTextFlow: StateFlow<String> = _statusTextFlow

    private val _alarmTime: MutableStateFlow<LocalTime?> = MutableStateFlow(null)
    val alarmTime: StateFlow<LocalTime?> = _alarmTime

    private val apiCalls by lazy { ApiCalls() }

    fun startAppFlow() {
        _statusTextFlow.value = "Getting permission."

        onRequestPermissions(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
    }

    suspend fun onPermissionsGranted(allPermissionsGranted: Boolean) {
        if (!allPermissionsGranted) {
            _statusTextFlow.value = "Permissions denied."
            return
        }

        _statusTextFlow.value = "Getting location."

        val location = getLocation() ?: return

        _statusTextFlow.value = "Getting sunrise / sunset times."

        val sunriseData = apiCalls.getSunriseData(location)

        if (sunriseData.getOrNull()?.sunrise == null) {
            Toast.makeText(context, "Cannot get sunrise data.", Toast.LENGTH_SHORT).show()
            return
        }

        _statusTextFlow.value = "Getting weather forecast."

        val weatherData = apiCalls.getWeatherData(location)

        if (weatherData.getOrNull() == null) {
            Toast.makeText(context, "Cannot get weather data. $weatherData", Toast.LENGTH_SHORT).show()
            return
        }

        _statusTextFlow.value += "\nWeather: ${weatherData.getOrNull()}"

        val sunrise = sunriseData.getOrThrow()!!.sunrise!!
        val weatherPoints = weatherData.getOrThrow()
        val weather = weatherPoints?.minBy { Duration.between(sunrise, it.date).toMillis().absoluteValue }

        if (weather == null) {
            _statusTextFlow.value = "Bad weather data."
            return
        }

        _statusTextFlow.value = "Sunrise: ${sunrise.toLocalTime()} \n" +
                "Weather: ${weather.ids.map { it.description }} at ${weather.date}"

        // TODO: tweak conditions
        val isGoodWeather = weather.ids.any { it.id in 800..802 }

        if (isGoodWeather) {
            _alarmTime.value = sunrise.minusMinutes(30).toLocalTime()
        }
    }

    fun setAlarm() {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up for the sunrise!")
            putExtra(AlarmClock.EXTRA_HOUR, alarmTime.value?.hour)
            putExtra(AlarmClock.EXTRA_MINUTES, alarmTime.value?.minute)
        }

        context.startActivity(intent)
    }

    private suspend fun getLocation(): Location? {
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        var location = fusedLocationClient.getLastLocationAsync()
        var maxRetries = 3

        while (location == null && maxRetries-- > 0) {
            delay(1000)
            location = fusedLocationClient.getLastLocationAsync()
        }

        if (location == null) {
            _statusTextFlow.value = "Cannot get location."
        }

        return location
    }

    private suspend fun FusedLocationProviderClient.getLastLocationAsync(): Location? = suspendCoroutine { cont ->
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED
        ) {
            _statusTextFlow.value = "Permission denied."
            cont.resume(null)
        }

        this.lastLocation
            .addOnSuccessListener { location: Location? ->
                cont.resume(location)
            }
            .addOnFailureListener {
                Log.i(this@AppLogic::class.simpleName, "Cannot get location: $it")
                cont.resume(null)
            }
    }
}
