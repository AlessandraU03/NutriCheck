package com.ale.nutricheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ale.nutricheck.core.di.AppContainer
import com.ale.nutricheck.core.ui.theme.AppTheme // O NutriCheckTheme según tu Theme.kt
import com.ale.nutricheck.features.nutricheck.di.NutriCheckModule
import com.ale.nutricheck.features.nutricheck.presentation.screens.NutriCheckScreen

class MainActivity : ComponentActivity() {
    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContainer = AppContainer(this)
        val nutriCheckModule = NutriCheckModule(appContainer)

        enableEdgeToEdge()

        setContent {
            AppTheme(darkTheme = true, dynamicColor = false) { // Forzamos tu diseño
                NutriCheckScreen(nutriCheckModule.provideFoodViewModelFactory())
            }
        }
    }
}