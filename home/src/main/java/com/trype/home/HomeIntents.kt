package com.trype.home

import com.trype.core.data.Alcohol

sealed interface HomeIntents {
    object GetMostEfficientAlcohol: HomeIntents
    object RefreshAlcohols: HomeIntents
    data class MostEfficientAlcoholClicked(val alcohol: Alcohol): HomeIntents
    data class CategoryClicked(val type: String): HomeIntents //TODO change type to enum
}