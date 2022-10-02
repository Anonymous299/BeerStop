package com.trype.search

import com.trype.core.data.Alcohol

sealed interface SearchIntents {
    object Loading: SearchIntents
    object Fetched: SearchIntents
    data class Error(val throwable: Throwable): SearchIntents
    object GetSpirits: SearchIntents
    data class AlcoholClicked(val alcohol: Alcohol): SearchIntents
    object ToggleFilter: SearchIntents
    data class ChangeCategory(val category: String): SearchIntents
    data class ChangeSubcategory(val subcategory: String): SearchIntents
    data class ChangeTitle(val title: String): SearchIntents
}