package com.trype.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavOptions

interface NavigationCommand{
    val arguments: List<NamedNavArgument>
    val destination: String
    val popBackStack: Boolean
}