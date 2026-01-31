package com.ale.nutricheck.features.nutricheck.data.datasources.remote.model

data class FoodResponse(
    val products: List<FoodDto>
)
data class FoodDto(
    val product_name: String?,
    val image_url: String?,
    val nutriments: Map<String, Any>?
)

data class NutrimentsDto(
    val sugars_100g: Double?,
    val energy_kcal_100g: Double?,
    val energy_kj_100g: Double?
)