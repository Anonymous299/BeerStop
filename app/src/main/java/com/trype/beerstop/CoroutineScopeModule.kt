package com.trype.beerstop

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

const val TAG = "CoroutineScopeModule"

@Retention
@Qualifier
annotation class MainImmediateScope // UI-related

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesScopeModule {

    @Singleton
    @Provides
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG,throwable.message.toString())
        }

    @MainImmediateScope
    @Singleton
    @Provides
    fun provideMainImmediateScope(
        @MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainImmediateDispatcher + exceptionHandler)
}
