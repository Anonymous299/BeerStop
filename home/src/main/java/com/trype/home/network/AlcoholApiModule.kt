package com.trype.home.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AlcoholApiModule {
    @Provides
    @Singleton
    fun provideAlcoholApi(
        retrofit: Retrofit
    ): AlcoholApi {
        return retrofit.create(AlcoholApi::class.java)
    }
}