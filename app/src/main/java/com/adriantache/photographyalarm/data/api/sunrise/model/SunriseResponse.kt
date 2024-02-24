package com.adriantache.photographyalarm.data.api.sunrise.model

import com.adriantache.photographyalarm.data.api.util.parseDate
import com.adriantache.photographyalarm.model.SunriseInfo
import kotlinx.serialization.Serializable
import java.time.LocalDate


@Serializable
data class SunriseResponse(
    val results: SunriseResponseData,
    val status: String,
) {
    fun toInfo(tomorrow: LocalDate): SunriseInfo? {
        return with(results) {
            SunriseInfo(
                sunrise = parseDate(sunrise, timezone, tomorrow) ?: return null,
                sunset = parseDate(sunset, timezone, tomorrow) ?: return null,
                firstLight = parseDate(first_light, timezone, tomorrow) ?: return null,
                lastLight = parseDate(last_light, timezone, tomorrow) ?: return null,
                dawn = parseDate(dawn, timezone, tomorrow) ?: return null,
                dusk = parseDate(dusk, timezone, tomorrow) ?: return null,
                solarNoon = parseDate(solar_noon, timezone, tomorrow) ?: return null,
                dayLength = day_length,
            )
        }
    }
}

@Serializable
@Suppress("PropertyName", "kotlin:S117")
data class SunriseResponseData(
    val sunrise: String,
    val sunset: String,
    val first_light: String,
    val last_light: String,
    val dawn: String,
    val dusk: String,
    val solar_noon: String,
    val day_length: String,
    val timezone: String,
    val utc_offset: Int,
)
