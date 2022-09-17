package com.trype.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
//TODO organize navigation command implementation
//object SearchNavigation {
//
//    fun searchCommand(
//    ) = object : NavigationCommand {
//
//        override val arguments: List<NamedNavArgument> = emptyList()
//
//        override val destination = NavigationDestinations.SearchDestination.route
//    }
//}

interface SearchNavigation{
    fun searchCommand(category: String): NavigationCommand
}