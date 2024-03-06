package com.adriantache.photographyalarm.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.R
import kotlinx.coroutines.launch

@Composable
fun SunriseToggle(
    isSunrise: Boolean,
    onSwitchSunrise: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val icon = if (isSunrise) R.drawable.noun_dawn_6475869 else R.drawable.noun_dusk_6475877

    var screenWidth by remember { mutableIntStateOf(0) }
    val offset = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSwitchSunrise()
            }
            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
            .onGloballyPositioned {
                screenWidth = it.size.width
            },
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x = offset.value.toInt(), y = 0)
                }
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                .onGloballyPositioned {
                    scope.launch {
                        val newOffset = if (isSunrise) {
                            0f
                        } else {
                            screenWidth - it.size.width.toFloat()
                        }
                        offset.animateTo(newOffset)
                    }
                }
                .padding(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.clickable { onSwitchSunrise() },
                    text = if (isSunrise) "Sunrise" else "Sunset",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}