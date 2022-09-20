package com.trype.home.domain

import com.trype.core.data.Alcohol
import com.trype.core.database.AlcoholDAO
import com.trype.home.network.AlcoholApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val alcoholDAO: AlcoholDAO,
    private val alcoholApi: AlcoholApi
) {
//TODO make networking more efficient
    fun getAlcohols(): Flow<Alcohol?> {
        return alcoholDAO
            .getMostEfficientAlcohol()
            .onEach { alcohol ->
                if(alcohol == null){
                    refreshAlcohols()
                }
            }
    }

    suspend fun refreshAlcohols(){
        withContext(Dispatchers.IO) {
            alcoholApi
                .getAlcoholList()
                .also {
                    alcoholDAO.saveAlcohols(it)
                }
        }
    }
}