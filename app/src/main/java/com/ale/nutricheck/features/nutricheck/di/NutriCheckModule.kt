package com.ale.nutricheck.features.nutricheck.di

import com.ale.nutricheck.core.di.AppContainer
import com.ale.nutricheck.features.nutricheck.domain.usecases.CalculateNutritionProgressUseCase
import com.ale.nutricheck.features.nutricheck.domain.usecases.GetFoodsUseCase
import com.ale.nutricheck.features.nutricheck.domain.usecases.UpdateDailyGoalUseCase
import com.ale.nutricheck.features.nutricheck.presentation.viewmodel.FoodViewModelFactory

class NutriCheckModule(private val appContainer: AppContainer) {

    private fun provideGetFoodsUseCase() = GetFoodsUseCase(appContainer.foodRepository)

    // Casos de uso de lógica de negocio pura (no necesitan repositorio aún)
    private fun provideUpdateDailyGoalUseCase() = UpdateDailyGoalUseCase()
    private fun provideCalculateProgressUseCase() = CalculateNutritionProgressUseCase()

    fun provideFoodViewModelFactory(): FoodViewModelFactory {
        return FoodViewModelFactory(
            getFoodsUseCase = provideGetFoodsUseCase(),
            updateDailyGoalUseCase = provideUpdateDailyGoalUseCase(),
            calculateProgressUseCase = provideCalculateProgressUseCase()
        )
    }
}