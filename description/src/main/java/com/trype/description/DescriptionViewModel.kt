package com.trype.description

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trype.core.extensions.resultOf
import com.trype.description.domain.DescriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val descriptionRepository: DescriptionRepository
): ViewModel() {
    private val eventChannel = Channel<DescriptionEvents>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    val uiState = MutableStateFlow(DescriptionUIState())
    private val intentFlow = MutableSharedFlow<DescriptionIntents>()

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
        savedStateHandle.get<String>("alcoholId")?.let {
            acceptIntent(DescriptionIntents.GetAlcohol(it.toInt()))
        }
    }

    fun acceptIntent(descriptionIntents: DescriptionIntents) {
        viewModelScope.launch {
            intentFlow.emit(descriptionIntents)
        }
    }

    fun reduceUiState(
        previousState: DescriptionUIState,
        partialState: DescriptionUIState.PartialState
    ): DescriptionUIState {
        return when (partialState) {
            is DescriptionUIState.PartialState.Loading -> {
                previousState.copy(
                    isLoading = true,
                    isError = false
                )
            }
            is DescriptionUIState.PartialState.Fetched -> {
                previousState.copy(
                    isLoading = false,
                    alcohol = partialState.alcohol,
                    isError = false
                )
            }
            is DescriptionUIState.PartialState.Error -> {
                previousState.copy(
                    isLoading = false,
                    isError = true
                )
            }
        }
    }

    private fun mapIntents(intent: DescriptionIntents): Flow<DescriptionUIState.PartialState> {
        return when (intent) {
            is DescriptionIntents.GetAlcohol -> getAlcohol(intent.id)
        }
    }

    private fun getAlcohol(id: Int): Flow<DescriptionUIState.PartialState> = flow {
        descriptionRepository.getAlcohol(id).map {
            resultOf { it }
        }
            .onStart {
                emit(DescriptionUIState.PartialState.Loading)
            }
            .collect { result ->
                result
                    .onSuccess { alcohol ->
                            emit(DescriptionUIState.PartialState.Fetched(alcohol))
                    }
                    .onFailure {
                        emit(DescriptionUIState.PartialState.Error(it))
                    }
            }
    }
}