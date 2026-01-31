package com.ale.nutricheck.features.nutricheck.domain.repositories

import com.ale.nutricheck.features.nutricheck.domain.entities.Food

interface FoodRepository {
    suspend fun getFoods(query: String): List<Food>
}