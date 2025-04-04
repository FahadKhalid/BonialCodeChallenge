package com.app.bonialcodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.bonialcodechallenge.presentation.ui.ContentScreen
import com.app.bonialcodechallenge.ui.theme.BonialCodeChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BonialCodeChallengeTheme { // Your custom theme
                ContentScreen()
            }
        }
    }
}