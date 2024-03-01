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
import com.adriantache.photographyalarm.data.Repository
import com.adriantache.photographyalarm.logic.AppState.Error
import com.adriantache.photographyalarm.logic.AppState.FindLocation
import com.adriantache.photographyalarm.logic.AppState.GetApiData
import com.adriantache.photographyalarm.logic.AppState.Init
import com.adriantache.photographyalarm.logic.AppState.RequestPermissions
import com.adriantache.photographyalarm.logic.AppState.Success
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppLogic(
    private val context: Context,
    private val onRequestPermissions: (Array<String>) -> Unit,
) {
    private val _statusFlow: MutableStateFlow<AppState> = MutableStateFlow(Init)
    val statusFlow: StateFlow<AppState> = _statusFlow

    private val repository by lazy { Repository(context) }

    private var isSunrise = true

    fun startAppFlow() {
        _statusFlow.value = RequestPermissions()

        onRequestPermissions(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
    }

    suspend fun onPermissionsGranted(allPermissionsGranted: Boolean) {
        if (!allPermissionsGranted) {
            _statusFlow.value = RequestPermissions(false)
            return
        }

        _statusFlow.value = FindLocation

        val location = getLocation() ?: return

        _statusFlow.value = GetApiData

        getData(location)
    }

    private suspend fun getData(location: Location) {
        val data = repository.getData(location, isSunrise)

        _statusFlow.value = if (data != null) {
            Success(
                results = data,
                onSwitchSunrise = {
                    isSunrise = !isSunrise

                    getData(location)
                }
            )
        } else {
            Error
        }
    }

    fun setAlarm(alarmTime: LocalTime) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up for the sunrise!")
            putExtra(AlarmClock.EXTRA_HOUR, alarmTime.hour)
            putExtra(AlarmClock.EXTRA_MINUTES, alarmTime.minute)
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
            Toast.makeText(context, "Cannot retrieve location!", Toast.LENGTH_SHORT).show()
        }

        return location
    }

    private suspend fun FusedLocationProviderClient.getLastLocationAsync(): Location? = suspendCoroutine { cont ->
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Location permission denied!", Toast.LENGTH_SHORT).show()
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
