package com.trype.home

import com.trype.core.data.Alcohol

data class HomeUIState(val isLoading: Boolean = false,
                       val mostEfficientAlcohol: Alcohol? = null,
                       val isError: Boolean = false) {
    sealed interface PartialState {
        object Loading : PartialState
        data class Fetched(val mostEfficientAlcohol: Alcohol) : PartialState
        data class Error(val throwable: Throwable) : PartialState
    }
}