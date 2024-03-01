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
import java.time.LocalDateTime

@Composable
fun WeatherView(weather: List<WeatherResultData>) {
    ItemCard(title = "WEATHER") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            WeatherItemView(iconUrl = weather[0].iconUrl, time = weather[0].time)

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "...",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            WeatherItemView(iconUrl = weather[1].iconUrl, time = weather[1].time)

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "...",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            WeatherItemView(iconUrl = weather[2].iconUrl, time = weather[2].time)
        }
    }
}

@Preview
@Composable
private fun WeatherViewPreview() {
    val iconUrl = "http://openweathermap.org/img/wn/01d@2x.png"

    WeatherView(
        listOf(
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

