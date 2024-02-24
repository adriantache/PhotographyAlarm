package com.adriantache.photographyalarm.data.api.weather.model

import com.adriantache.photographyalarm.data.api.util.parseTimestamp
import com.adriantache.photographyalarm.model.WeatherInfo
import com.adriantache.photographyalarm.model.WeatherInfoPoint
import kotlinx.serialization.Serializable

private const val WEATHER_ICON_1 = "https://openweathermap.org/img/wn/"
private const val WEATHER_ICON_2 = "@2x.png"

@Serializable
data class WeatherResponse(
    val list: List<WeatherResponseDatapoint>,
) {
    fun getFormattedList(): List<WeatherInfo> {
        return list.mapNotNull { datapoint ->
            val date = parseTimestamp(datapoint.dt) ?: return@mapNotNull null

            WeatherInfo(
                date = date,
                ids = datapoint.weather.map {
                    WeatherInfoPoint(
                        id = it.id,
                        main = it.main,
                        description = it.description,
                        iconUrl = WEATHER_ICON_1 + it.icon + WEATHER_ICON_2,
                    )
                },
            )
        }
    }
}

@Serializable
data class WeatherResponseDatapoint(
    val dt: Long, //time of forecast
    val weather: List<WeatherResponseWeather>,
    val clouds: WeatherResponseClouds,
)

@Serializable
data class WeatherResponseClouds(
    val all: Int, // cloud cover percentage
)

@Serializable
data class WeatherResponseWeather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

/**
 * todo add conversion for weather ids:
 *
 * Group 2xx: Thunderstorm
 * ID	Main	Description	Icon
 * 200	Thunderstorm	thunderstorm with light rain	 11d
 * 201	Thunderstorm	thunderstorm with rain	 11d
 * 202	Thunderstorm	thunderstorm with heavy rain	 11d
 * 210	Thunderstorm	light thunderstorm	 11d
 * 211	Thunderstorm	thunderstorm	 11d
 * 212	Thunderstorm	heavy thunderstorm	 11d
 * 221	Thunderstorm	ragged thunderstorm	 11d
 * 230	Thunderstorm	thunderstorm with light drizzle	 11d
 * 231	Thunderstorm	thunderstorm with drizzle	 11d
 * 232	Thunderstorm	thunderstorm with heavy drizzle	 11d
 * Group 3xx: Drizzle
 * ID	Main	Description	Icon
 * 300	Drizzle	light intensity drizzle	 09d
 * 301	Drizzle	drizzle	 09d
 * 302	Drizzle	heavy intensity drizzle	 09d
 * 310	Drizzle	light intensity drizzle rain	 09d
 * 311	Drizzle	drizzle rain	 09d
 * 312	Drizzle	heavy intensity drizzle rain	 09d
 * 313	Drizzle	shower rain and drizzle	 09d
 * 314	Drizzle	heavy shower rain and drizzle	 09d
 * 321	Drizzle	shower drizzle	 09d
 * Group 5xx: Rain
 * ID	Main	Description	Icon
 * 500	Rain	light rain	 10d
 * 501	Rain	moderate rain	 10d
 * 502	Rain	heavy intensity rain	 10d
 * 503	Rain	very heavy rain	 10d
 * 504	Rain	extreme rain	 10d
 * 511	Rain	freezing rain	 13d
 * 520	Rain	light intensity shower rain	 09d
 * 521	Rain	shower rain	 09d
 * 522	Rain	heavy intensity shower rain	 09d
 * 531	Rain	ragged shower rain	 09d
 * Group 6xx: Snow
 * ID	Main	Description	Icon
 * 600	Snow	light snow	 13d
 * 601	Snow	snow	 13d
 * 602	Snow	heavy snow	 13d
 * 611	Snow	sleet	 13d
 * 612	Snow	light shower sleet	 13d
 * 613	Snow	shower sleet	 13d
 * 615	Snow	light rain and snow	 13d
 * 616	Snow	rain and snow	 13d
 * 620	Snow	light shower snow	 13d
 * 621	Snow	shower snow	 13d
 * 622	Snow	heavy shower snow	 13d
 * Group 7xx: Atmosphere
 * ID	Main	Description	Icon
 * 701	Mist	mist	 50d
 * 711	Smoke	smoke	 50d
 * 721	Haze	haze	 50d
 * 731	Dust	sand/dust whirls	 50d
 * 741	Fog	fog	 50d
 * 751	Sand	sand	 50d
 * 761	Dust	dust	 50d
 * 762	Ash	volcanic ash	 50d
 * 771	Squall	squalls	 50d
 * 781	Tornado	tornado	 50d
 * Group 800: Clear
 * ID	Main	Description	Icon
 * 800	Clear	clear sky	 01d
 *  01n
 * Group 80x: Clouds
 * ID	Main	Description	Icon
 * 801	Clouds	few clouds: 11-25%	 02d
 *  02n
 * 802	Clouds	scattered clouds: 25-50%	 03d
 *  03n
 * 803	Clouds	broken clouds: 51-84%	 04d
 *  04n
 * 804	Clouds	overcast clouds: 85-100%	 04d
 *  04n
 */
