package com.ale.nutricheck.features.nutricheck.data.di

import com.ale.nutricheck.features.nutricheck.data.repositories.FoodRepositoryImpl
import com.ale.nutricheck.features.nutricheck.domain.repositories.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFoodRepository(
        foodRepositoryImpl: FoodRepositoryImpl
    ): FoodRepository

}
