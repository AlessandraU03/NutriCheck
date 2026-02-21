package com.ale.nutricheck.features.nutricheck.data.datasources.remote.api

import com.ale.nutricheck.features.nutricheck.data.datasources.remote.model.FoodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFoodFactsApi {
    @GET("cgi/search.pl")
    suspend fun getFoods(
        @Query("search_terms") query: String,
        @Query("json") json: Int = 1
    ): FoodResponse
}