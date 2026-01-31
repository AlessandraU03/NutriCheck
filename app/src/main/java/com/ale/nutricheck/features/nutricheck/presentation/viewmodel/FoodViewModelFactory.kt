package com.ale.nutricheck.features.nutricheck.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ale.nutricheck.features.nutricheck.domain.usecases.GetFoodsUseCase
import com.ale.nutricheck.features.nutricheck.domain.usecases.UpdateDailyGoalUseCase
import com.ale.nutricheck.features.nutricheck.domain.usecases.CalculateNutritionProgressUseCase

class FoodViewModelFactory(
    private val getFoodsUseCase: GetFoodsUseCase,
    private val updateDailyGoalUseCase: UpdateDailyGoalUseCase,
    private val calculateProgressUseCase: CalculateNutritionProgressUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodViewModel(
                getFoodsUseCase = getFoodsUseCase,
                updateDailyGoalUseCase = updateDailyGoalUseCase,
                calculateProgressUseCase = calculateProgressUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}