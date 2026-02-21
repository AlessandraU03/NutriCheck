package com.ale.nutricheck.features.nutricheck.data.di
import com.ale.nutricheck.core.di.OpenFoodFactsRetrofit
import com.ale.nutricheck.features.nutricheck.data.datasources.remote.api.OpenFoodFactsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OpenFoodFactNetworkModule {
    @Provides
    @Singleton
    fun provideOpenFoodFactsApi(@OpenFoodFactsRetrofit retrofit: Retrofit): OpenFoodFactsApi{
        return retrofit.create(OpenFoodFactsApi::class.java)
    }
}
