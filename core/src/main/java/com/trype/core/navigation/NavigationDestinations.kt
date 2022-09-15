package com.trype.core.navigation

sealed class NavigationDestinations(val route: String) {
    object SearchDestination: NavigationDestinations("search")
    object HomeDestination: NavigationDestinations("home")
}
