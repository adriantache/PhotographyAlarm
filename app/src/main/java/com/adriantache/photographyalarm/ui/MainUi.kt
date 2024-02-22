package com.adriantache.photographyalarm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.logic.AppLogic
import com.adriantache.photographyalarm.logic.AppState.FindLocation
import com.adriantache.photographyalarm.logic.AppState.GetSunrise
import com.adriantache.photographyalarm.logic.AppState.GetWeather
import com.adriantache.photographyalarm.logic.AppState.Init
import com.adriantache.photographyalarm.logic.AppState.RequestPermissions
import com.adriantache.photographyalarm.logic.AppState.Success
import com.adriantache.photographyalarm.ui.view.StatusView

@Composable
fun MainUi(appLogic: AppLogic) {
    val status by appLogic.statusFlow.collectAsState()

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
        when (val localStatus = status) {
            FindLocation -> StatusView("Getting location data...")
            GetSunrise -> StatusView("Getting sunrise data...")
            GetWeather -> StatusView("Getting weather data...")
            Init -> Unit
            is RequestPermissions -> Text("Requesting permissions...")
            is Success -> SuccessScreen(data = localStatus.results) {
                localStatus.results.alarmTime?.let { appLogic.setAlarm(it) }
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
