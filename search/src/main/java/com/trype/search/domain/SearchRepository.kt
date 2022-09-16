package com.trype.search.domain

import com.trype.core.data.Alcohol
import com.trype.core.database.AlcoholDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val alcoholDAO: AlcoholDAO
) {
    //TODO add logic for networking
    fun getSpirits(category: Set<String>): Flow<List<Alcohol>> {
        return alcoholDAO
            .getSpirits(category)
    }
}