package com.example.dailypromptapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dailypromptapp.navigation.AppNavHost
import com.example.dailypromptapp.ui.theme.DailyPromptAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyPromptAppTheme {
                AppNavHost()
            }
        }
    }
}
