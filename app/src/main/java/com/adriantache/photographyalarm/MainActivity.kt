package com.adriantache.photographyalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.adriantache.photographyalarm.logic.AppLogic
import com.adriantache.photographyalarm.ui.MainUi
import com.adriantache.photographyalarm.ui.theme.PhotographyAlarmTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val appLogic: AppLogic by lazy {
        AppLogic(
            context = this,
            onRequestPermissions = { requestMultiplePermissionsLauncher.launch(it) },
        )
    }

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            lifecycleScope.launch {
                appLogic.onPermissionsGranted(allPermissionsGranted = permissions.all { it.value })
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhotographyAlarmTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainUi(appLogic)
                }
            }
        }
    }
}
