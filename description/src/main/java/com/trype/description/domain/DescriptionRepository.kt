package com.trype.description.domain

import com.trype.core.data.Alcohol
import com.trype.core.database.AlcoholDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DescriptionRepository @Inject constructor(
    private val alcoholDAO: AlcoholDAO
) {
    fun getAlcohol(id: Int): Flow<Alcohol> {
        return alcoholDAO.getAlcoholFromId(id)
    }
}