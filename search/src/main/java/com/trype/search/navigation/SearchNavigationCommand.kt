package com.trype.search.navigation

import androidx.navigation.NamedNavArgument
import com.trype.core.navigation.NavigationCommand
import com.trype.core.navigation.NavigationDestinations
import com.trype.core.navigation.SearchNavigation
import javax.inject.Inject

class SearchNavigationCommand @Inject constructor() : SearchNavigation {
    override fun searchCommand(category: String) = object: NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()

        override val destination = "${NavigationDestinations.SearchDestination.base_route}/$category"

        override val popBackStack = false
    }
}