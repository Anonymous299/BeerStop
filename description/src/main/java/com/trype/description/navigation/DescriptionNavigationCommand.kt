package com.trype.description.navigation

import androidx.navigation.NamedNavArgument
import com.trype.core.data.Alcohol
import com.trype.core.navigation.DescriptionNavigation
import com.trype.core.navigation.NavigationCommand
import com.trype.core.navigation.NavigationDestinations
import javax.inject.Inject

class DescriptionNavigationCommand @Inject constructor() : DescriptionNavigation {
    override fun descriptionCommand(alcoholId: Int) = object: NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()

        override val destination = "${NavigationDestinations.DescriptionDestination.base_route}/$alcoholId"
    }
}