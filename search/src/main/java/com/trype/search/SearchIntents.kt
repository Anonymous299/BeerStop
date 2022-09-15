package com.trype.search

import com.trype.core.data.Alcohol

sealed interface SearchIntents {
    data class GetSpirits(val category: String): SearchIntents
    data class AlcoholClicked(val alcohol: Alcohol): SearchIntents
}