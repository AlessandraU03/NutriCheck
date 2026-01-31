package com.ale.nutricheck.features.nutricheck.domain.entities

data class Food(
    val name: String,
    val imageUrl: String,
    val sugar: Double,
    val calories: Double,
    val proteins: Double = 0.0,
    val fats: Double = 0.0,
    val carbs: Double = 0.0,
    val isHealthy: Boolean = false
)
