package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.model.WeatherResultData
import com.adriantache.photographyalarm.model.WeatherResultSummary
import java.time.LocalDateTime

@Composable
fun WeatherView(weather: WeatherResultSummary) {
    ItemCard(title = "WEATHER") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            WeatherItemView(
                iconUrl = weather.before.iconUrl,
                time = weather.before.time
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "...",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            WeatherItemView(
                iconUrl = weather.closest.iconUrl,
                time = weather.closest.time
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "...",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            WeatherItemView(
                iconUrl = weather.after.iconUrl,
                time = weather.after.time
            )
        }
    }
}

@Preview
@Composable
private fun WeatherViewPreview() {
    val iconUrl = "http://openweathermap.org/img/wn/01d@2x.png"

    WeatherView(
        WeatherResultSummary(
            WeatherResultData(
                description = "",
                iconUrl = iconUrl,
                time = LocalDateTime.now(),
                ids = emptyList(),
            ),
            WeatherResultData(
                description = "",
                iconUrl = iconUrl,
                time = LocalDateTime.now(),
                ids = emptyList(),
            ),
            WeatherResultData(
                description = "",
                iconUrl = iconUrl,
                time = LocalDateTime.now(),
                ids = emptyList(),
            ),
        )
    )
}

