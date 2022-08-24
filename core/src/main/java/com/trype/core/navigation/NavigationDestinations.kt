package com.trype.core.navigation

sealed class NavigationDestinations(val route: String) {
    object EfficientAlcohol: NavigationDestinations("effalcohol")
}
