package com.ale.nutricheck.core.network

import com.ale.nutricheck.features.nutricheck.data.datasources.remote.model.FoodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NutriApi {
    // Volvemos a la consulta base más estable
    @GET("cgi/search.pl?search_simple=1&json=1")
    suspend fun searchProducts(@Query("search_terms") query: String): FoodResponse
}
