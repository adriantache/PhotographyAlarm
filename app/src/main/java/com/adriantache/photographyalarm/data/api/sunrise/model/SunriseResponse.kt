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
                firstLight = first_light?.let { parseDate(it, timezone, tomorrow) },
                lastLight = last_light?.let { parseDate(it, timezone, tomorrow) },
                dawn = dawn?.let { parseDate(it, timezone, tomorrow) },
                dusk = dusk?.let { parseDate(it, timezone, tomorrow) },
                solarNoon = solar_noon?.let { parseDate(it, timezone, tomorrow) },
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
    val first_light: String?,
    val last_light: String?,
    val dawn: String?,
    val dusk: String?,
    val solar_noon: String?,
    val day_length: String?,
    val timezone: String,
    val utc_offset: Int?,
)
