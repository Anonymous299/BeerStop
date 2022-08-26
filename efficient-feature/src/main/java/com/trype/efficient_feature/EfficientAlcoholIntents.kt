package com.trype.efficient_feature

sealed interface EfficientAlcoholIntents {
    object GetAlcohols: EfficientAlcoholIntents
    object RefreshAlcohols: EfficientAlcoholIntents
    data class AlcoholClicked(val uri: String): EfficientAlcoholIntents
}