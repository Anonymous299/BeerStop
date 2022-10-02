package com.trype.search

import android.os.Parcel
import android.os.Parcelable
import com.trype.core.data.Alcohol
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchUIState(val isLoading: Boolean = false,
                       val category: Set<String> = emptySet(),
                         val subcategory: Set<String> = emptySet(),
                         val title: String = "",
                       val isError: Boolean = false,
                        val showFilter: Boolean = false): Parcelable {


    sealed interface PartialState {
        object Loading : PartialState
        object Fetched : PartialState
        data class Error(val throwable: Throwable) : PartialState
        object FilterToggled: PartialState
        data class CategoryChanged(val categorySet: Set<String>): PartialState
        data class SubcategoryChanged(val subcategorySet: Set<String>): PartialState
        data class TitleChanged(val title: String): PartialState
    }

//    companion object CREATOR : Parcelable.Creator<SearchUIState> {
//        override fun createFromParcel(parcel: Parcel): SearchUIState {
//            return SearchUIState(parcel)
//        }
//
//        override fun newArray(size: Int): Array<SearchUIState?> {
//            return arrayOfNulls(size)
//        }
//    }
}