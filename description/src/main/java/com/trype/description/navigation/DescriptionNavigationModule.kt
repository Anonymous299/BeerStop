package com.trype.description.navigation

import com.trype.core.navigation.DescriptionNavigation
import com.trype.core.navigation.SearchNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DescriptionNavigationModule {

    @Binds
    @Singleton
    fun bindDescriptionNavigation(impl: DescriptionNavigationCommand): DescriptionNavigation
}