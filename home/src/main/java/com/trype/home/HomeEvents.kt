package com.trype.home

import com.trype.core.data.Alcohol
import com.trype.core.navigation.NavigationCommand

sealed interface HomeEvents {
    data class OpenMostEfficientDetails(val alcohol: Alcohol): HomeEvents
    data class OpenCategorySearch(val type: String, val navigationCommand: NavigationCommand): HomeEvents //TODO change type to enum
}