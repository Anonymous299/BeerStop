package com.trype.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class MainImmediateDispatcher // UI-related

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesDispatcherModule {

    @MainImmediateDispatcher
    @Singleton
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}