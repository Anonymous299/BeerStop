package com.trype.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.trype.core.data.Alcohol
import com.trype.core.extensions.resultOf
import com.trype.core.navigation.DescriptionNavigation
import com.trype.core.navigation.NavigationManager
import com.trype.search.domain.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val descriptionNavigation: DescriptionNavigation,
    val navigationManager: NavigationManager,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val SEARCH_STATE_KEY = "searchState"
    private val eventChannel = Channel<SearchEvents>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    val searchResults: MutableStateFlow<List<Alcohol>> = MutableStateFlow(emptyList())

    val uiState: StateFlow<SearchUIState> =
        savedStateHandle.getStateFlow(SEARCH_STATE_KEY, SearchUIState())
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
        val initialCategory: String? = savedStateHandle["category"]
        if (initialCategory != null) {
            acceptIntent(SearchIntents.ChangeCategory(initialCategory))
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
            is SearchUIState.PartialState.FilterToggled ->
                previousState.copy(
                    showFilter = !uiState.value.showFilter
                )
            is SearchUIState.PartialState.CategoryChanged -> {
                acceptIntent(SearchIntents.GetSpirits)
                previousState.copy(
                    category = partialState.categorySet
                )
            }
        }
    }

    private fun mapIntents(intent: SearchIntents): Flow<SearchUIState.PartialState> {
        return when (intent) {
            is SearchIntents.GetSpirits -> getSpirits()
            is SearchIntents.AlcoholClicked -> openDescriptionPage(intent.alcohol)
            is SearchIntents.ToggleFilter -> flow {
                emit(SearchUIState.PartialState.FilterToggled)
            }
            is SearchIntents.ChangeCategory -> changeCategory(intent.category)
        }
    }

    private fun changeCategory(category: String): Flow<SearchUIState.PartialState> {
        val categorySet: HashSet<String> = uiState.value.category.toHashSet()
        if (!categorySet.contains(category)) {
            categorySet.add(category)
        } else {
            if (categorySet.size == 1) {
                return emptyFlow()
            }
            categorySet.remove(category)
        }
        return flow{
        emit(SearchUIState.PartialState.CategoryChanged(categorySet))
    }
}

    private fun openDescriptionPage(alcohol: Alcohol): Flow<SearchUIState.PartialState> {
        viewModelScope.launch {
            eventChannel.send(SearchEvents.OpenAlcoholDetails(descriptionNavigation.descriptionCommand(alcohol.id)))
        }
        return emptyFlow()
    }

    private fun getSpirits(): Flow<SearchUIState.PartialState> = flow {
        searchRepository.getSpirits(uiState.value.category).map {
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