package com.ale.nutricheck.features.nutricheck.domain.usecases

class CalculateNutritionProgressUseCase {
    operator fun invoke(consumed: Double, goal: Double): Float {
        if (goal <= 0) return 0f
        return (consumed / goal).toFloat().coerceIn(0f, 1.1f)
    }
}