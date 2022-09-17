package com.trype.description.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.trype.core.data.Alcohol
import com.trype.core.navigation.NavigationDestinations
import com.trype.description.presentation.DescriptionScreen
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import javax.inject.Inject

@HiltComposeNavigationFactory
class DescriptionNavigationFactory @Inject constructor(): ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(NavigationDestinations.HomeDestination.route){   backStackEntry ->
            DescriptionScreen(alcohol = backStackEntry.arguments!!.get("alcohol") as Alcohol)
        }
    }
}