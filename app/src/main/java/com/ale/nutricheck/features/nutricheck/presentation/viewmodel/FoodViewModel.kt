package com.ale.nutricheck.features.nutricheck.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ale.nutricheck.features.nutricheck.domain.entities.Food
import com.ale.nutricheck.features.nutricheck.domain.usecases.GetFoodsUseCase
import com.ale.nutricheck.features.nutricheck.presentation.screens.NutriCheckUiState
import com.ale.nutricheck.features.nutricheck.domain.usecases.UpdateDailyGoalUseCase
import com.ale.nutricheck.features.nutricheck.domain.usecases.CalculateNutritionProgressUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodViewModel(
    private val getFoodsUseCase: GetFoodsUseCase,
    private val updateDailyGoalUseCase: UpdateDailyGoalUseCase,
    private val calculateProgressUseCase: CalculateNutritionProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutriCheckUiState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null
    private var lastQuery: String = "" 
    private var allFoodsCache: List<Food> = emptyList()

    init {
        searchFoods("jugo", debounce = false)
    }

    fun searchFoods(query: String, debounce: Boolean = true) {
        if (query == lastQuery && allFoodsCache.isNotEmpty()) {
            applyLocalFilter()
            return
        }
        
        searchJob?.cancel()
        
        if (query.isEmpty()) {
            lastQuery = ""
            allFoodsCache = emptyList()
            _uiState.update { it.copy(isLoading = false, foods = emptyList(), error = null) }
            return
        }

        lastQuery = query
        allFoodsCache = emptyList() 

        _uiState.update { it.copy(
            isLoading = true, 
            error = null,
            foods = emptyList() 
        ) }

        searchJob = viewModelScope.launch {
            if (debounce) {
                delay(600)
            }
            
            val result = getFoodsUseCase(query, 0.0)

            result.fold(
                onSuccess = { list ->
                    allFoodsCache = list
                    applyLocalFilter()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    private fun applyLocalFilter() {
        val goal = _uiState.value.dailyCalorieGoal
        // FILTRO DE UMBRAL: Solo mostramos alimentos que no superen la meta diaria
        val filtered = if (goal > 0) {
            allFoodsCache.filter { it.calories <= goal }
        } else {
            allFoodsCache
        }
        
        _uiState.update { current ->
            current.copy(
                isLoading = false,
                foods = filtered
            )
        }
    }

    fun onGoalEntered(input: String) {
        val newGoal = updateDailyGoalUseCase(input)
        
        if (input.isEmpty() || newGoal <= 0) {
            // REINICIO TOTAL AL BORRAR LA META
            allFoodsCache = emptyList()
            lastQuery = ""
            _uiState.update { 
                NutriCheckUiState(
                    dailyCalorieGoal = 0.0,
                    consumedCalories = 0.0,
                    progress = 0f,
                    foods = emptyList(),
                    snackbarMessage = null
                ) 
            }
        } else {
            // AL CAMBIAR LA META: Reseteamos consumo y limpiamos lista para forzar nueva búsqueda adaptada
            _uiState.update { it.copy(
                dailyCalorieGoal = newGoal,
                consumedCalories = 0.0,
                progress = 0f,
                foods = emptyList() // Limpiamos para evitar mostrar productos que ya no "caben"
            ) }
            // Si había una búsqueda activa, volvemos a filtrar con el nuevo umbral
            if (lastQuery.isNotEmpty()) {
                applyLocalFilter()
            }
        }
    }

    fun addFoodToIntake(food: Food) {
        if (!food.isHealthy) {
            _uiState.update { it.copy(snackbarMessage = "⚠️ Este alimento es poco saludable. ¡Intenta con una opción mejor!") }
        }

        _uiState.update { state ->
            val totalConsumed = state.consumedCalories + food.calories
            state.copy(
                consumedCalories = totalConsumed,
                progress = calculateProgressUseCase(totalConsumed, state.dailyCalorieGoal)
            )
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
