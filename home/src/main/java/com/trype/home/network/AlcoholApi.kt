package com.trype.home.network

import com.trype.core.data.Alcohol
import retrofit2.http.GET

interface AlcoholApi {
    @GET("/api/alcohol?numberOfResults=9999999999")
    suspend fun getAlcoholList(): List<Alcohol>
}