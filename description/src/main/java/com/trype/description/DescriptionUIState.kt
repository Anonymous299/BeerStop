package com.trype.description

import com.trype.core.data.Alcohol

data class DescriptionUIState(val isLoading: Boolean = false,
                       val alcohol: Alcohol? = null,
                       val isError: Boolean = false) {
    sealed interface PartialState {
        object Loading : PartialState
        data class Fetched(val alcohol: Alcohol) : PartialState
        data class Error(val throwable: Throwable) : PartialState
    }
}