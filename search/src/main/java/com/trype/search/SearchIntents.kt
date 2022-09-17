package com.trype.search

import com.trype.core.data.Alcohol

sealed interface SearchIntents {
    data class ToggleFilter(val open: Boolean): SearchIntents
    data class GetSpirits(val category: Set<String>): SearchIntents
    data class AlcoholClicked(val alcohol: Alcohol): SearchIntents
    data class SaveCategory(val category: Set<String>): SearchIntents
}