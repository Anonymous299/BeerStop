package com.trype.efficient_feature.network

import retrofit2.http.GET

interface EfficientApi {
    @GET("/api/alcohol")
    suspend fun getEfficientList(): String
}