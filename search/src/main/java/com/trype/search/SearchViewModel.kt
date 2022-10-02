package com.trype.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.trype.core.data.Alcohol
import com.trype.core.database.buildQuery
import com.trype.core.extensions.resultOf
import com.trype.core.navigation.DescriptionNavigation
import com.trype.core.navigation.NavigationManager
import com.trype.search.data.FilterState
import com.trype.search.domain.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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

    private var filterFlow = MutableStateFlow(FilterState())

    //TODO add this to ui state
    val searchResults: Flow<List<Alcohol>> = filterFlow.flatMapLatest { filterState ->
        searchRepository.getSpirits(
               buildQuery(
                   filterState.category, filterState.subcategory,
                   filterState.title
               )
           ).map {
               resultOf { it }
        }.onStart {
            acceptIntent(SearchIntents.Loading)
        }.transform { result ->
            result.onSuccess {
                emit(it)
            }.onFailure {
                acceptIntent(SearchIntents.Error(it))
            }
        }
    }

    val subCategoryResults: Flow<List<String>> = filterFlow.flatMapLatest { filterState ->
        searchRepository.getSubcategories(filterState.category).transform { subcategoryList ->
            emit(subcategoryList.distinct())
        }
   }

    val uiState: StateFlow<SearchUIState> =
        savedStateHandle.getStateFlow(SEARCH_STATE_KEY, SearchUIState())
    private val intentFlow = MutableSharedFlow<SearchIntents>()

    init {
        viewModelScope.launch {
            intentFlow.flatMapMerge {
                Log.d("SahilTest", "intent: $it")
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
                    category = partialState.categorySet,
                    subcategory = emptySet()
                )
            }
            is SearchUIState.PartialState.SubcategoryChanged -> {
                acceptIntent(SearchIntents.GetSpirits)
                previousState.copy(
                    subcategory = partialState.subcategorySet
                )
            }
            is SearchUIState.PartialState.TitleChanged -> {
                acceptIntent(SearchIntents.GetSpirits)
                previousState.copy(
                    title = partialState.title
                )
            }
        }
    }

    private fun mapIntents(intent: SearchIntents): Flow<SearchUIState.PartialState> {
        return when (intent) {
            is SearchIntents.GetSpirits -> getSpirits()
            is SearchIntents.AlcoholClicked -> openDescriptionPage(intent.alcohol)
            is SearchIntents.ToggleFilter -> flowOf(SearchUIState.PartialState.FilterToggled)
            is SearchIntents.ChangeCategory -> changeCategory(intent.category)
            is SearchIntents.ChangeSubcategory -> changeSubcategory(intent.subcategory)
            is SearchIntents.ChangeTitle -> flowOf(SearchUIState.PartialState.TitleChanged(intent.title))
            is SearchIntents.Loading -> flowOf(SearchUIState.PartialState.Loading)
            is SearchIntents.Fetched -> flowOf(SearchUIState.PartialState.Fetched)
            is SearchIntents.Error -> flowOf(SearchUIState.PartialState.Error(intent.throwable))
        }
    }

    private fun changeSubcategory(subcategory: String): Flow<SearchUIState.PartialState> {
        val subcategorySet: HashSet<String> = uiState.value.subcategory.toHashSet()
        if (!subcategorySet.contains(subcategory)) {
            subcategorySet.add(subcategory)
        } else {
            subcategorySet.remove(subcategory)
        }
        return flowOf(SearchUIState.PartialState.SubcategoryChanged(subcategorySet))
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
        return flowOf(SearchUIState.PartialState.CategoryChanged(categorySet))
}

    private fun openDescriptionPage(alcohol: Alcohol): Flow<SearchUIState.PartialState> {
        viewModelScope.launch {
            eventChannel.send(SearchEvents.OpenAlcoholDetails(descriptionNavigation.descriptionCommand(alcohol.rowid)))
        }
        return emptyFlow()
    }

    private fun getSpirits(): Flow<SearchUIState.PartialState> {
           viewModelScope.launch {
               filterFlow.emit(FilterState(uiState.value.category,
               uiState.value.subcategory,
               uiState.value.title))
           }
            return emptyFlow()
    }
}