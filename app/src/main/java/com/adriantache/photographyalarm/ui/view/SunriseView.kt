package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.model.SunriseResultData

@Composable
fun SunriseView(sunrise: List<SunriseResultData>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SunriseItemView(
            iconRes = sunrise[0].iconRes,
            time = sunrise[0].time
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "...",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        SunriseItemView(
            iconRes = sunrise[1].iconRes,
            time = sunrise[1].time
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "...",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        SunriseItemView(
            iconRes = sunrise[2].iconRes,
            time = sunrise[2].time
        )
    }
}
