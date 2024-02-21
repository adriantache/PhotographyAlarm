package com.adriantache.photographyalarm.logic

sealed interface AppState {
    data object Init : AppState


}
