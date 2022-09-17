package com.trype.description

sealed interface DescriptionIntents {
    data class GetAlcohol(val id: Int): DescriptionIntents
}