package com.ale.nutricheck.features.nutricheck.data.repositories

import com.ale.nutricheck.core.network.NutriApi
import com.ale.nutricheck.features.nutricheck.data.datasources.remote.mapper.toDomain
import com.ale.nutricheck.features.nutricheck.domain.repositories.FoodRepository
import com.ale.nutricheck.features.nutricheck.domain.entities.Food

class FoodRepositoryImpl(
    private val api: NutriApi
) : FoodRepository {
    override suspend fun getFoods(query: String): List<Food> {
        val response = api.searchProducts(query)
        return response.products.map { it.toDomain() }
    }
}