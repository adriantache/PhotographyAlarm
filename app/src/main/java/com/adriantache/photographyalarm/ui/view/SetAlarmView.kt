package com.adriantache.photographyalarm.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.R
import java.time.LocalTime

@Composable
fun SetAlarmView(
    shouldSetAlarm: Boolean,
    alarmTime: LocalTime,
    onSetAlarm: () -> Unit,
) {
    val buttonColor = if (shouldSetAlarm) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = { onSetAlarm() },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.requiredSize(56.dp),
                    painter = painterResource(id = R.drawable.alarm_icon),
                    contentDescription = null
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "$alarmTime AM",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SetAlarmViewPreview() {
    SetAlarmView(
        shouldSetAlarm = true,
        alarmTime = LocalTime.now(),
        onSetAlarm = {}
    )
}
