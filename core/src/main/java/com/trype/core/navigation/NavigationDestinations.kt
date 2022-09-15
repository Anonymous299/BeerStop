package com.trype.core.navigation

sealed class NavigationDestinations(val base_route: String, val argument_route: String = "",
                                    val route: String = "$base_route/{$argument_route}"
) {
    object SearchDestination: NavigationDestinations("search", "category")
    object HomeDestination: NavigationDestinations("home")
}
