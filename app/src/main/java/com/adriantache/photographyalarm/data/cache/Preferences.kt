package com.adriantache.photographyalarm.data.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.adriantache.photographyalarm.model.ResultData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val USER_PREFERENCES_NAME = "user_preferences"
private const val CACHE_KEY = "CACHE_KEY"

class Preferences(
    context: Context,
    private val preferences: SharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE),
) {
    fun saveCache(data: ResultData) {
        preferences.edit {
            putString(CACHE_KEY, data.convertToJson())
        }
    }

    fun getCache(): ResultData? {
        if (!preferences.contains(CACHE_KEY)) return null

        return preferences.getString(CACHE_KEY, null)?.convertToResultData()
    }

    private fun ResultData.convertToJson(): String {
        return Json.encodeToString(this)
    }

    private fun String.convertToResultData(): ResultData {
        return Json.decodeFromString(this)
    }
}
