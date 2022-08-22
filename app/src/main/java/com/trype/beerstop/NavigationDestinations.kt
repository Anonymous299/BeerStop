package com.trype.beerstop

sealed class NavigationDestinations(val route: String) {
    object EfficientAlcohol: NavigationDestinations("effalcohol")
}
