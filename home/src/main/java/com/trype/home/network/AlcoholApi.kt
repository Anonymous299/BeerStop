package com.trype.home.network

import com.trype.core.data.Alcohol
import retrofit2.http.GET

interface AlcoholApi {
    @GET("/api/alcohol/efficient")
    suspend fun getAlcoholList(): List<Alcohol>
}