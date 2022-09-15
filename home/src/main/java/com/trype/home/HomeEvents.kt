package com.trype.home

import com.trype.core.data.Alcohol

sealed interface HomeEvents {
    data class OpenMostEfficientDetails(val alcohol: Alcohol): HomeEvents
    data class OpenCategorySearch(val type: String): HomeEvents //TODO change type to enum
}