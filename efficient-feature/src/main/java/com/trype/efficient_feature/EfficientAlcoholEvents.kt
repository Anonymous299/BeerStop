package com.trype.efficient_feature

sealed interface EfficientAlcoholEvents {
    data class OpenAlcoholInWebBrowser(val uri: String): EfficientAlcoholEvents
}