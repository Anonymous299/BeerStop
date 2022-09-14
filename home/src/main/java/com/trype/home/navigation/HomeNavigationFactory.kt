package com.trype.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.trype.core.navigation.NavigationDestinations
import com.trype.home.presentation.HomeScreen
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import javax.inject.Inject

@HiltComposeNavigationFactory
class HomeNavigationFactory @Inject constructor(): ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(NavigationDestinations.HomeDestination.route){
            HomeScreen()
        }
    }
}