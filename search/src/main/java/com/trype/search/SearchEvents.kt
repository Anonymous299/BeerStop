package com.trype.search

import com.trype.core.data.Alcohol
import com.trype.core.navigation.NavigationCommand

sealed interface SearchEvents {
    data class OpenAlcoholDetails(val navigationCommand: NavigationCommand): SearchEvents
}