package com.ale.nutricheck.features.nutricheck.data.repositories

import android.util.Log
import com.ale.nutricheck.features.nutricheck.data.datasources.remote.api.OpenFoodFactsApi
import com.ale.nutricheck.features.nutricheck.data.datasources.remote.mapper.toDomain
import com.ale.nutricheck.features.nutricheck.domain.repositories.FoodRepository
import com.ale.nutricheck.features.nutricheck.domain.entities.Food
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val api: OpenFoodFactsApi
) : FoodRepository {
    override suspend fun getFoods(query: String): List<Food> {
        val response = api.getFoods(query)
        Log.d("OpenFoodFacts", response.toString())
        return response.products.map { it.toDomain() }
    }
}