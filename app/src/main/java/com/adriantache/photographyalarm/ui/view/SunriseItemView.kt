package com.adriantache.photographyalarm.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SunriseItemView(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    time: String,
) {
    val lightColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val darkColor = if (lightColor == Color.White) Color.Black else Color.White

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(lightColor, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.requiredSize(48.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = darkColor,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            color = darkColor,
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
