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
import com.adriantache.photographyalarm.model.SunriseResultSummary

@Composable
fun SunriseView(sunrise: SunriseResultSummary) {
    ItemCard(title = "SUNRISE") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SunriseItemView(
                iconRes = sunrise.firstLight.iconRes,
                time = sunrise.firstLight.time
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "...",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            SunriseItemView(
                iconRes = sunrise.dawn.iconRes,
                time = sunrise.dawn.time
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "...",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            SunriseItemView(
                iconRes = sunrise.sunrise.iconRes,
                time = sunrise.sunrise.time
            )
        }
    }
}