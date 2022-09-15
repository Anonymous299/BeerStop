package com.trype.search

import com.trype.core.data.Alcohol

data class SearchUIState(val isLoading: Boolean = false,
                       val searchResults: List<Alcohol> = emptyList(),
                       val isError: Boolean = false) {
    sealed interface PartialState {
        object Loading : PartialState
        data class Fetched(val searchResults: List<Alcohol>) : PartialState
        data class Error(val throwable: Throwable) : PartialState
    }
}