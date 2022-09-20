package com.trype.search

import com.trype.core.data.Alcohol

sealed interface SearchIntents {
    object GetSpirits: SearchIntents
    data class AlcoholClicked(val alcohol: Alcohol): SearchIntents
    object ToggleFilter: SearchIntents
    data class ChangeCategory(val category: String): SearchIntents
}