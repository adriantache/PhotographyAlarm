package com.adriantache.photographyalarm.data.cache

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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

        val cache = preferences.getString(CACHE_KEY, null) ?: return null

        val decoded = cache.convertToResultData()

        if (decoded == null) {
            // Must be some kind of deserialization error, better to clear the cache.
            preferences.edit { remove(CACHE_KEY) }
        }

        return decoded
    }

    private fun ResultData.convertToJson(): String {
        return Json.encodeToString(this)
    }

    private fun String.convertToResultData(): ResultData? {
        return try {
            Json.decodeFromString(this)
        } catch (e: Exception) {
            Log.e(this@Preferences::class.simpleName, "Cannot decode json: $this", e)

            null
        }
    }
}
