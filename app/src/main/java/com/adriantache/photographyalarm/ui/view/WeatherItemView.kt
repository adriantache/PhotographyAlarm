package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WeatherItemView(
    iconUrl: String,
    time: String,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(Color.Black, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier.requiredSize(48.dp),
            model = iconUrl,
            contentDescription = null,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            color = Color.White,
            text = time,
        )
    }
}

@Preview(backgroundColor = 0xfff)
@Composable
private fun WeatherItemViewPreview() {
    val iconUrl = "http://openweathermap.org/img/wn/01d@2x.png"

    WeatherItemView(
        iconUrl = iconUrl,
        time = "12:25",
    )
}
