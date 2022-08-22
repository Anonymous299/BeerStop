package com.trype.beerstop

import kotlinx.coroutines.flow.Flow

interface NavigationManager {
    val navigationEvent: Flow<NavigationCommand>
    fun navigate(command: NavigationCommand)
}