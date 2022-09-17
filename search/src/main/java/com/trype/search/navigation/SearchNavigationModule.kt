package com.trype.search.navigation

import com.trype.core.navigation.NavigationManager
import com.trype.core.navigation.SearchNavigation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SearchNavigation {

    @Binds
    @Singleton
    fun bindSearchNavigation(impl: SearchNavigationCommand): SearchNavigation
}