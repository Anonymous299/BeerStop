package com.trype.efficient_feature.network

import com.trype.efficient_feature.data.Alcohol
import retrofit2.http.GET

interface EfficientApi {
    @GET("/api/alcohol")
    suspend fun getEfficientList(): List<Alcohol>
}