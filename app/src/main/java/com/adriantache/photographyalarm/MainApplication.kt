package com.adriantache.photographyalarm

import android.app.Application
import com.adriantache.photographyalarm.api.ApiCalls
import com.adriantache.photographyalarm.room.AppRoomDatabase

class MainApplication : Application() {
    val database by lazy { AppRoomDatabase.getDatabase(this) }
    val apiCalls by lazy { ApiCalls(database.dao()) }
}
