package com.adriantache.photographyalarm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.logic.AppLogic

@Composable
fun MainUi(appLogic: AppLogic) {
    val statusText by appLogic.statusTextFlow.collectAsState()
    val alarmTimeStatus by appLogic.alarmTime.collectAsState()

    LaunchedEffect(Unit) {
        appLogic.startAppFlow()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Status: $statusText",
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                painterResource(id = android.R.drawable.ic_menu_rotate),
                modifier = Modifier
                    .clickable { appLogic.startAppFlow() }
                    .requiredSize(24.dp),
                contentDescription = null,
            )
        }

        if (alarmTimeStatus != null) {
            Button(onClick = { appLogic.setAlarm() }) {
                Text("Set alarm for tomorrow morning!")
            }
        }
    }
}

@Preview(backgroundColor = 0xfff)
@Composable
private fun MainUiPreview() {
    MainUi(
        AppLogic(
            context = LocalContext.current,
            onRequestPermissions = {}
        )
    )
}
