package com.app.bonialcodechallenge

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.app.bonialcodechallenge.presentation.ui.BrochureScreen
import com.app.bonialcodechallenge.ui.theme.BonialCodeChallengeTheme
import com.app.bonialcodechallenge.utils.NetworkObserver
import com.app.bonialcodechallenge.utils.NetworkUtils
import com.app.bonialcodechallenge.utils.NetworkViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkObserver = NetworkObserver(this)
        val networkViewModel = NetworkViewModel(networkObserver)

        setContent {
            BonialCodeChallengeTheme {
                val isConnected by networkViewModel.networkStatus.collectAsState()

                LaunchedEffect(isConnected) {
                    if (!isConnected) {
                        Toast.makeText(
                            this@MainActivity,
                            applicationContext.getString(R.string.no_internet),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                BrochureScreen()
            }
        }
    }
}