package com.trype.core.navigation

import androidx.navigation.NavOptions

interface NavigationCommand{
    val configuration: NavOptions
    val destination: String
}