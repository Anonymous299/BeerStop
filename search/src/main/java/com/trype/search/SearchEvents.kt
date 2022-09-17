package com.trype.search

import com.trype.core.data.Alcohol

sealed interface SearchEvents {
    data class OpenAlcoholDetails(val alcohol: Alcohol): SearchEvents
}