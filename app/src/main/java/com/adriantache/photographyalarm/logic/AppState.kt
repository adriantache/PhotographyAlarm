package com.adriantache.photographyalarm.logic

import com.adriantache.photographyalarm.model.ResultData

sealed interface AppState {
    data object Init : AppState

    data class RequestPermissions(val arePermissionsGranted: Boolean? = null) : AppState

    data object FindLocation : AppState

    data object GetApiData : AppState

    data class Success(val results: ResultData) : AppState

    data object Error : AppState
}
