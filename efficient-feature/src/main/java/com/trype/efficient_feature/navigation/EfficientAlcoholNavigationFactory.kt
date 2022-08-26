package com.trype.efficient_feature.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.trype.core.navigation.NavigationDestinations
import com.trype.efficient_feature.presentation.EfficientAlcoholPage
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import javax.inject.Inject

@HiltComposeNavigationFactory
class EfficientAlcoholNavigationFactory @Inject constructor(): ComposeNavigationFactory {
    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(NavigationDestinations.EfficientAlcohol.route){
            EfficientAlcoholPage()
        }
    }
}