package com.example.digiapi.di

import com.example.digiapi.data.remote.DigimonApiService
import com.example.digiapi.data.remote.ApiClient
import com.example.digiapi.data.repository.DigimonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDigimonApiService(): DigimonApiService {
        return ApiClient.digimonApiService
    }

    @Provides
    @Singleton
    fun provideDigimonRepository(apiService: DigimonApiService): DigimonRepository {
        return DigimonRepository(apiService)
    }
}