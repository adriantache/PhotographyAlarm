package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SunriseToggle(
    isSunrise: Boolean,
    onSwitchSunrise: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Button(onClick = onSwitchSunrise) {
            Text(
                modifier = Modifier.clickable { onSwitchSunrise() },
                text = if (isSunrise) "Switch to Sunset" else "Switch to Sunrise",
            )
        }
    }
}