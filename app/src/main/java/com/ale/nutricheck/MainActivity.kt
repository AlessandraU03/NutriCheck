package com.ale.nutricheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ale.nutricheck.core.ui.theme.AppTheme
import com.ale.nutricheck.features.nutricheck.presentation.screens.NutriCheckScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppTheme {
                NutriCheckScreen()
            }
        }
    }
}