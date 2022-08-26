package com.trype.efficient_feature.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EfficientApiModule {
    @Provides
    @Singleton
    fun provideEffcientApi(
        retrofit: Retrofit
    ): EfficientApi {
        return retrofit.create(EfficientApi::class.java)
    }
}