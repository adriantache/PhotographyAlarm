package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adriantache.photographyalarm.model.ResultData

@Composable
fun TimelineView(data: ResultData) {
    val localDensity = LocalDensity.current.density

    var screenWidth by remember { mutableIntStateOf(0) }
    val screenWidthDp by remember {
        derivedStateOf {
            screenWidth / localDensity
        }
    }

    val lightColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val darkColor = if (lightColor == Color.White) Color.Black else Color.White

    ItemCard(
        title = "TIMELINE",
        backgroundColor = lightColor
    ) {
        Box(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .onGloballyPositioned {
                screenWidth = it.size.width
            }
        ) {

            val firstPoint = if (data.isSunrise) data.sunrise.firstLight else data.sunset.sunset
            val offset1 = data.getPercent(firstPoint.timestamp)
            Icon(
                modifier = Modifier
                    .requiredSize(48.dp)
                    .offset(y = 0.dp, x = (screenWidthDp * offset1 - 48 * offset1).dp),
                painter = painterResource(id = firstPoint.iconRes),
                contentDescription = null,
                tint = darkColor,
            )

            val secondPoint = if (data.isSunrise) data.sunrise.dawn else data.sunset.dusk
            val offset2 = data.getPercent(secondPoint.timestamp)
            Icon(
                modifier = Modifier
                    .requiredSize(48.dp)
                    .offset(y = 0.dp, x = (screenWidthDp * offset2 - 48 * offset2).dp),
                painter = painterResource(id = secondPoint.iconRes),
                contentDescription = null,
                tint = darkColor,
            )

            val thirdPoint = if (data.isSunrise) data.sunrise.sunrise else data.sunset.lastLight
            val offset3 = data.getPercent(thirdPoint.timestamp)
            Icon(
                modifier = Modifier
                    .requiredSize(48.dp)
                    .offset(y = 0.dp, x = (screenWidthDp * offset3 - 48 * offset3).dp),
                painter = painterResource(id = thirdPoint.iconRes),
                contentDescription = null,
                tint = darkColor,
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(data.minTime.toString(), color = darkColor)

            Spacer(Modifier.weight(1f))
            Text("...", color = darkColor)
            Spacer(Modifier.weight(1f))

            Text(data.midPoint.toString(), color = darkColor)

            Spacer(Modifier.weight(1f))
            Text("...", color = darkColor)
            Spacer(Modifier.weight(1f))

            Text(data.maxTime.toString(), color = darkColor)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.Black, RoundedCornerShape(8.dp))
        ) {
            val offset1 = data.getPercent(data.weather.before.timestamp)
            AsyncImage(
                modifier = Modifier
                    .requiredSize(48.dp)
                    .offset(y = 0.dp, x = (screenWidthDp * offset1 - 48 * offset1).dp),
                model = data.weather.before.iconUrl,
                contentDescription = null,
            )

            val offset2 = data.getPercent(data.weather.closest.timestamp)
            AsyncImage(
                modifier = Modifier
                    .requiredSize(48.dp)
                    .offset(y = 0.dp, x = (screenWidthDp * offset2 - 48 * offset2).dp),
                model = data.weather.closest.iconUrl,
                contentDescription = null,
            )

            val offset3 = data.getPercent(data.weather.after.timestamp)
            AsyncImage(
                modifier = Modifier
                    .requiredSize(48.dp)
                    .offset(y = 0.dp, x = (screenWidthDp * offset3 - 48 * offset3).dp),
                model = data.weather.after.iconUrl,
                contentDescription = null,
            )
        }
    }
}
