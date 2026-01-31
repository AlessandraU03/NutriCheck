package com.ale.nutricheck.features.nutricheck.data.datasources.remote.mapper

import com.ale.nutricheck.features.nutricheck.data.datasources.remote.model.FoodDto
import com.ale.nutricheck.features.nutricheck.domain.entities.Food

fun FoodDto.toDomain(): Food {
    val kcal = (nutriments?.get("energy-kcal_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("energy_kcal_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("energy-kcal") as? Number)?.toDouble()
        ?: (nutriments?.get("energy_kcal") as? Number)?.toDouble()

    val kj = (nutriments?.get("energy-kj_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("energy_kj_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("energy-kj") as? Number)?.toDouble()
        ?: (nutriments?.get("energy_kj") as? Number)?.toDouble()

    val sugars = (nutriments?.get("sugars_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("sugars_serving") as? Number)?.toDouble()
        ?: 0.0
        
    val proteins = (nutriments?.get("proteins_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("proteins_serving") as? Number)?.toDouble()
        ?: 0.0
        
    val fats = (nutriments?.get("fat_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("fat_serving") as? Number)?.toDouble()
        ?: 0.0
        
    val carbs = (nutriments?.get("carbohydrates_100g") as? Number)?.toDouble()
        ?: (nutriments?.get("carbohydrates_serving") as? Number)?.toDouble()
        ?: 0.0

    val finalCalories = when {
        kcal != null && kcal > 0 -> kcal
        kj != null && kj > 0 -> kj / 4.184
        else -> 0.0
    }

    return Food(
        name = this.product_name ?: "Producto desconocido",
        imageUrl = this.image_url ?: "",
        sugar = sugars,
        calories = finalCalories,
        proteins = proteins,
        fats = fats,
        carbs = carbs,
        isHealthy = sugars < 5.0 && fats < 10.0 // Criterio de salud más completo
    )
}
