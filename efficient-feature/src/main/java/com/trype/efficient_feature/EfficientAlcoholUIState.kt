package com.trype.efficient_feature

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.trype.efficient_feature.data.Alcohol
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class EfficientAlcoholUIState(
    val isLoading: Boolean = false,
    val alcoholList: List<Alcohol> = emptyList(),
    val isError: Boolean = false
) : Parcelable {
    sealed interface PartialState {
        object Loading : PartialState
        data class Fetched(val alcoholList: List<Alcohol>) : PartialState
        data class Error(val throwable: Throwable) : PartialState
    }
}
