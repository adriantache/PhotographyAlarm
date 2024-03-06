package com.adriantache.photographyalarm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adriantache.photographyalarm.logic.AppLogic
import com.adriantache.photographyalarm.logic.AppState.Error
import com.adriantache.photographyalarm.logic.AppState.FindLocation
import com.adriantache.photographyalarm.logic.AppState.GetApiData
import com.adriantache.photographyalarm.logic.AppState.Init
import com.adriantache.photographyalarm.logic.AppState.RequestPermissions
import com.adriantache.photographyalarm.logic.AppState.Success
import com.adriantache.photographyalarm.ui.view.StatusView
import kotlinx.coroutines.launch

@Composable
fun MainUi(appLogic: AppLogic) {
    val scope = rememberCoroutineScope()

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

            GetApiData -> StatusView("Getting sunrise and weather data...")

            Init -> Unit

            is RequestPermissions -> Text("Requesting permissions...")

            is Success -> SuccessScreen(
                data = localStatus.results,
                onSetAlarm = { appLogic.setAlarm(localStatus.results.alarmTime) },
                onSwitchSunrise = {
                    scope.launch {
                        localStatus.onSwitchSunrise()
                    }
                }
            )

            Error -> Box(
                modifier = Modifier.clickable { appLogic.startAppFlow() }
            ) {
                StatusView("Error retrieving data.")
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
