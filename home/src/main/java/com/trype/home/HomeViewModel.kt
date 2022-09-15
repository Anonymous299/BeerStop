package com.trype.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trype.core.data.Alcohol
import com.trype.core.extensions.resultOf
import com.trype.home.domain.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val eventChannel = Channel<HomeEvents>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    val uiState = MutableStateFlow(HomeUIState())
    private val intentFlow = MutableSharedFlow<HomeIntents>()

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
        acceptIntent(HomeIntents.GetMostEfficientAlcohol)
    }

    fun acceptIntent(homeIntent: HomeIntents) {
        viewModelScope.launch {
            intentFlow.emit(homeIntent)
        }
    }

    fun reduceUiState(
        previousState: HomeUIState,
        partialState: HomeUIState.PartialState
    ): HomeUIState {
        return when (partialState) {
            is HomeUIState.PartialState.Loading -> {
                previousState.copy(
                    isLoading = true,
                    isError = false
                )
            }
            is HomeUIState.PartialState.Fetched -> {
                previousState.copy(
                    isLoading = false,
                    mostEfficientAlcohol = partialState.mostEfficientAlcohol,
                    isError = false
                )
            }
            is HomeUIState.PartialState.Error -> {
                previousState.copy(
                    isLoading = false,
                    isError = true
                )
            }
        }
    }

    private fun mapIntents(intent: HomeIntents): Flow<HomeUIState.PartialState> {
        return when (intent) {
            is HomeIntents.GetMostEfficientAlcohol -> getMostEfficientAlcohol()
            is HomeIntents.RefreshAlcohols -> refreshAlcohols()
            is HomeIntents.MostEfficientAlcoholClicked -> openDescriptionPage(intent.alcohol)
            is HomeIntents.CategoryClicked -> openCategorySearch(intent.type)
        }
    }

    private fun openDescriptionPage(alcohol: Alcohol): Flow<HomeUIState.PartialState> {
        //TODO implement
        return emptyFlow()
    }

    private fun openCategorySearch(type: String): Flow<HomeUIState.PartialState>{
        //TODO implement
        return emptyFlow()
    }

    private fun getMostEfficientAlcohol(): Flow<HomeUIState.PartialState> = flow {
        homeRepository.getAlcohols().map {
            resultOf { it }
        }
            .onStart {
                emit(HomeUIState.PartialState.Loading)
            }
            .collect { result ->
                result
                    .onSuccess { alcohol ->
                        if(alcohol == null){
                            emit(HomeUIState.PartialState.Loading)
                        }
                        else{
                            emit(HomeUIState.PartialState.Fetched(alcohol))
                        }
                    }
                    .onFailure {
                        emit(HomeUIState.PartialState.Error(it))
                    }
            }
    }

    private fun refreshAlcohols(): Flow<HomeUIState.PartialState> = flow {

        resultOf { homeRepository.refreshAlcohols() }
            .onFailure {
                emit(HomeUIState.PartialState.Error(it))
            }
    }
}