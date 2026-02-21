package com.ale.nutricheck.features.nutricheck.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ale.nutricheck.features.nutricheck.domain.usecases.GetFoodsUseCase
import com.ale.nutricheck.features.nutricheck.presentation.state.NutriCheckUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val getFoodsUseCase: GetFoodsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutriCheckUiState())
    val uiState = _uiState.asStateFlow()

    fun searchFoods(query: String) {
        if (query.isBlank()) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = getFoodsUseCase(query, _uiState.value.maxCalories)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { list ->
                        currentState.copy(isLoading = false, foods = list)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, error = error.message)
                    }
                )
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _uiState.update { it.copy(searchText = text) }
        searchFoods(text)
    }

    fun onGoalEntered(goal: String) {
        val calories = goal.toDoubleOrNull() ?: 0.0
        _uiState.update { it.copy(maxCalories = calories) }
    }

    fun addFoodToIntake(food: com.ale.nutricheck.features.nutricheck.domain.entities.Food) {
        _uiState.update { currentState ->
            val newTotal = currentState.totalCalories + food.calories
            val message = if (currentState.maxCalories > 0 && newTotal > currentState.maxCalories) {
                "⚠️ Has superado tu meta calórica"
            } else {
                "${food.name} agregado"
            }
            currentState.copy(totalCalories = newTotal, snackbarMessage = message)
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}