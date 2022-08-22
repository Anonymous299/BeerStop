package com.trype.beerstop

import androidx.navigation.NavOptions

data class NavigationCommand(val destination: String,
                             val configuration: NavOptions = NavOptions.Builder().build())