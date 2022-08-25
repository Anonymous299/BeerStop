package com.trype.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

const val LCBO_API_URL = "http://68.183.108.111/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(
    ): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl(LCBO_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}