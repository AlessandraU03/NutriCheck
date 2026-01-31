package com.ale.nutricheck.features.nutricheck.domain.usecases

class UpdateDailyGoalUseCase {
    operator fun invoke(input: String): Double {
        return input.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
    }
}