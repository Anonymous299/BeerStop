package com.trype.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
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
    private val searchRepository: SearchRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val SEARCH_STATE_KEY = "searchState"
    private val eventChannel = Channel<SearchEvents>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    val searchResults: MutableStateFlow<List<Alcohol>> = MutableStateFlow(emptyList())

    val uiState: StateFlow<SearchUIState> = savedStateHandle.getStateFlow(SEARCH_STATE_KEY, SearchUIState())
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
                    savedStateHandle[SEARCH_STATE_KEY] = it
                }
        }
        Log.d("SahilTest", "savedStateHandle contains: ${savedStateHandle.contains("category")}")
//        viewModelScope.launch {
//            category.collectLatest {
//                Log.d("SahilTest", "category:$it")
//                acceptIntent(SearchIntents.GetSpirits(category=it))
//            }
//        }
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
                viewModelScope.launch {
                    searchResults.emit(partialState.searchResults)
                }
                previousState.copy(
                    isLoading = false,
                    isError = false
                )
            }
            is SearchUIState.PartialState.Error -> {
                previousState.copy(
                    isLoading = false,
                    isError = true
                )
            }
            is SearchUIState.PartialState.CategoryChanged -> {
                acceptIntent(SearchIntents.GetSpirits(partialState.category))
                previousState.copy(
                    category = partialState.category
                )
            }
            is SearchUIState.PartialState.FilterChanged ->
                previousState.copy(
                    filter = partialState.open
                )
        }
    }

    private fun mapIntents(intent: SearchIntents): Flow<SearchUIState.PartialState> {
        return when (intent) {
            is SearchIntents.GetSpirits -> getSpirits(intent.category)
            is SearchIntents.AlcoholClicked -> openDescriptionPage(intent.alcohol)
            is SearchIntents.SaveCategory -> saveCategory(intent.category)
            is SearchIntents.ToggleFilter -> toggleFilter(intent.open)
        }
    }

    private fun toggleFilter(open: Boolean): Flow<SearchUIState.PartialState> = flow{
        emit(SearchUIState.PartialState.FilterChanged(open))
    }

    private fun saveCategory(category: Set<String>): Flow<SearchUIState.PartialState> = flow {
        Log.d("SahilTest", "category changed1")
        emit(SearchUIState.PartialState.CategoryChanged(category))
    }

    private fun openDescriptionPage(alcohol: Alcohol): Flow<SearchUIState.PartialState> {
        //TODO implement
        return emptyFlow()
    }

    private fun getSpirits(category: Set<String>): Flow<SearchUIState.PartialState> = flow {
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