package com.trype.efficient_feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trype.core.extensions.resultOf
import com.trype.efficient_feature.domain.EfficientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

@HiltViewModel
class EfficientAlcoholViewModel @Inject constructor(
    private val efficientRepository: EfficientRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, EfficientAlcoholUIState())
    private val intentFlow = MutableSharedFlow<EfficientAlcoholIntents>()

    init {
        viewModelScope.launch {
            intentFlow.flatMapMerge {
                mapIntents(it)
            }.scan(uiState.value, ::reduceUiState)
                .catch {
                    //TODO handle this
                }
                .collect {
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
        acceptIntent(EfficientAlcoholIntents.GetAlcohols)
    }

    fun acceptIntent(efficientAlcoholIntent: EfficientAlcoholIntents) {
        viewModelScope.launch {
            intentFlow.emit(efficientAlcoholIntent)
        }
    }

    fun reduceUiState(
        previousState: EfficientAlcoholUIState,
        partialState: EfficientAlcoholUIState.PartialState
    ): EfficientAlcoholUIState {
        return when (partialState) {
            is EfficientAlcoholUIState.PartialState.Loading -> {
                previousState.copy(
                    isLoading = true,
                    isError = false
                )
            }
            is EfficientAlcoholUIState.PartialState.Fetched -> {
                previousState.copy(
                    isLoading = false,
                    alcoholList = partialState.alcoholList,
                    isError = false
                )
            }
            is EfficientAlcoholUIState.PartialState.Error -> {
                previousState.copy(
                    isLoading = false,
                    isError = true
                )
            }
        }
    }

    private fun mapIntents(intent: EfficientAlcoholIntents): Flow<EfficientAlcoholUIState.PartialState> {
        return when (intent) {
            EfficientAlcoholIntents.GetAlcohols -> getAlcohols()
            EfficientAlcoholIntents.RefreshAlcohols -> refreshAlcohols()
        }
    }

    private fun getAlcohols(): Flow<EfficientAlcoholUIState.PartialState> = flow {
        efficientRepository.getAlcohols().map {
            resultOf { it }
        }
            .onStart {
                emit(EfficientAlcoholUIState.PartialState.Loading)
            }
            .collect { result ->
                result
                    .onSuccess { alcoholList ->
                        emit(EfficientAlcoholUIState.PartialState.Fetched(alcoholList))
                    }
                    .onFailure {
                        emit(EfficientAlcoholUIState.PartialState.Error(it))
                    }
            }
    }

    private fun refreshAlcohols(): Flow<EfficientAlcoholUIState.PartialState> = flow {

        resultOf { efficientRepository.refreshAlcohols() }
            .onFailure {
                emit(EfficientAlcoholUIState.PartialState.Error(it))
            }
    }
}