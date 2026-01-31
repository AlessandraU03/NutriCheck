package com.ale.nutricheck.features.nutricheck.presentation.screens
import com.ale.nutricheck.features.nutricheck.domain.entities.Food

data class NutriCheckUiState(
    val isLoading: Boolean = false,
    val foods: List<Food> = emptyList(),
    val error: String? = null,
    val dailyCalorieGoal: Double = 0.0,
    val consumedCalories: Double = 0.0,
    val progress: Float = 0f,
    val snackbarMessage: String? = null
)