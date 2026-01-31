package com.ale.nutricheck.core.di

import android.content.Context
import com.ale.nutricheck.BuildConfig
import com.ale.nutricheck.core.network.NutriApi
import com.ale.nutricheck.features.nutricheck.data.repositories.FoodRepositoryImpl
import com.ale.nutricheck.features.nutricheck.domain.repositories.FoodRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class AppContainer(context: Context) {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "NutriCheckApp - Android - Version 1.0 - alessandro@example.com") // REQUERIDO Y MÁS ESPECÍFICO
                .build()
            chain.proceed(request)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        
    val nutriApi: NutriApi by lazy {
        retrofit.create(NutriApi::class.java)
    }

    val foodRepository: FoodRepository by lazy {
        FoodRepositoryImpl(nutriApi)
    }
}
