package com.adriantache.photographyalarm.logic

sealed interface AppState {
    data object Init : AppState

    data class RequestPermissions(val arePermissionsGranted: Boolean? = null) : AppState

    data object FindLocation : AppState

    data object GetSunrise : AppState

    data object GetWeather : AppState

    data class Success(val results: ResultData) : AppState
}
