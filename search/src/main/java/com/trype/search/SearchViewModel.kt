package com.trype.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trype.core.data.Alcohol
import com.trype.core.extensions.resultOf
import com.trype.search.domain.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {
    //TODO save and get category from savedstatehandle
    private val eventChannel = Channel<SearchEvents>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    val uiState = MutableStateFlow(SearchUIState())
    private val intentFlow = MutableSharedFlow<SearchIntents>()

    init {
        viewModelScope.launch {
            intentFlow.flatMapMerge {
                mapIntents(it)
            }.scan(uiState.value, ::reduceUiState)
                .catch {
                    //TODO handle this
                }
                .collect {
                    uiState.emit(it)
                }
        }

    }

    fun acceptIntent(searchIntent: SearchIntents) {
        viewModelScope.launch {
            intentFlow.emit(searchIntent)
        }
    }

    fun reduceUiState(
        previousState: SearchUIState,
        partialState: SearchUIState.PartialState
    ): SearchUIState {
        return when (partialState) {
            is SearchUIState.PartialState.Loading -> {
                previousState.copy(
                    isLoading = true,
                    isError = false
                )
            }
            is SearchUIState.PartialState.Fetched -> {
                previousState.copy(
                    isLoading = false,
                    searchResults = partialState.searchResults,
                    isError = false
                )
            }
            is SearchUIState.PartialState.Error -> {
                previousState.copy(
                    isLoading = false,
                    isError = true
                )
            }
        }
    }

    private fun mapIntents(intent: SearchIntents): Flow<SearchUIState.PartialState> {
        return when (intent) {
            is SearchIntents.GetSpirits -> getSpirits(intent.category)
            is SearchIntents.AlcoholClicked -> openDescriptionPage(intent.alcohol)
        }
    }

    private fun openDescriptionPage(alcohol: Alcohol): Flow<SearchUIState.PartialState> {
        //TODO implement
        return emptyFlow()
    }

    private fun getSpirits(category: String): Flow<SearchUIState.PartialState> = flow {
        searchRepository.getSpirits(category).map {
            resultOf { it }
        }
            .onStart {
                emit(SearchUIState.PartialState.Loading)
            }
            .collect { result ->
                result
                    .onSuccess { spiritList ->
                            emit(SearchUIState.PartialState.Fetched(spiritList))

                    }
                    .onFailure {
                        emit(SearchUIState.PartialState.Error(it))
                    }
            }
    }
}