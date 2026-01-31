package com.ale.nutricheck.features.nutricheck.domain.usecases

import com.ale.nutricheck.features.nutricheck.domain.repositories.FoodRepository
import com.ale.nutricheck.features.nutricheck.domain.entities.Food
import kotlinx.coroutines.CancellationException

class GetFoodsUseCase(private val repository: FoodRepository) {
   suspend operator fun invoke(query: String, maxCalories: Double): Result<List<Food>> {
        return try {
            val foods = repository.getFoods(query)

            val filteredFoods = if (maxCalories > 0) {
                foods.filter { it.calories <= maxCalories }
            } else {
                foods
            }

            val processedFoods = filteredFoods.map { it.copy(isHealthy = it.sugar < 10.0) }
            Result.success(processedFoods)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }
}
