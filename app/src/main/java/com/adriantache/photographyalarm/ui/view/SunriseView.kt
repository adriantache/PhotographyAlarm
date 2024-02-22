package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SunriseView(sunrise: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(100.dp),
        text = "Sunrise at $sunrise",
    )
}
