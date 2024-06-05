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
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.model.SunsetResultSummary

@Composable
fun SunsetView(sunset: SunsetResultSummary) {
    ItemCard(title = "SUNSET") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SunriseItemView(
                iconRes = sunset.sunset.iconRes,
                time = sunset.sunset.time
            )

            sunset.dusk?.let {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "...",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                SunriseItemView(
                    iconRes = sunset.dusk.iconRes,
                    time = sunset.dusk.time
                )
            }

            sunset.lastLight?.let {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "...",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                SunriseItemView(
                    iconRes = sunset.lastLight.iconRes,
                    time = sunset.lastLight.time
                )
            }
        }
    }
}