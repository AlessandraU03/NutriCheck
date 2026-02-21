package com.ale.nutricheck.features.nutricheck.presentation.state

import com.ale.nutricheck.features.nutricheck.domain.entities.Food

data class NutriCheckUiState(
    val searchText: String = "",
    val foods: List<Food> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalCalories: Double = 0.0,
    val maxCalories: Double = 0.0,
    val snackbarMessage: String? = null
)