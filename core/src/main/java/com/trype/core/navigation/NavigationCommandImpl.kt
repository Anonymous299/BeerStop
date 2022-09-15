package com.trype.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object SearchNavigation {

    private val SEARCH_CATEGORY = "category"

    fun searchCommand(
        category: String? = null
    ) = object : NavigationCommand {

        override val arguments = listOf(
            navArgument(SEARCH_CATEGORY) { type = NavType.StringType }
        )

        override val destination = "search/$category"
    }
}