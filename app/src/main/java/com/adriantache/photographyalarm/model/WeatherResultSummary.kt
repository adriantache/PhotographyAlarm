package com.adriantache.photographyalarm.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResultSummary(
    val before: WeatherResultData,
    val closest: WeatherResultData,
    val after: WeatherResultData,
) {
    val shouldSetAlarm = closest.ids.any { it in 800..802 }
}
