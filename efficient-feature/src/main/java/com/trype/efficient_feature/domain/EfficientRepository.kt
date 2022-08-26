package com.trype.efficient_feature.domain

import android.util.Log
import com.trype.efficient_feature.data.Alcohol
import com.trype.efficient_feature.database.EfficientDAO
import com.trype.efficient_feature.network.EfficientApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EfficientRepository @Inject constructor(
    private val efficientDAO: EfficientDAO,
    private val efficientApi: EfficientApi
) {

    fun getAlcohols(): Flow<List<Alcohol>> {
        return efficientDAO
            .getAlcohols()
            .onEach { alcohols ->
                if (alcohols.isEmpty()) {
                    refreshAlcohols()
                }
            }
    }

    suspend fun refreshAlcohols(){
        withContext(Dispatchers.IO) {
                efficientApi
                    .getEfficientList()
                    .also {
                        efficientDAO.saveAlcohols(it)
                    }
        }
    }
}