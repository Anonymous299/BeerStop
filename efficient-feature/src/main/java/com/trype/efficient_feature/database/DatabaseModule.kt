package com.trype.efficient_feature.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAlcoholDatabase(
        @ApplicationContext context: Context
    ): AlcoholDatabase{
        return Room.databaseBuilder(context,
            AlcoholDatabase::class.java,
            "alcohol").build()
    }

    @Provides
    fun provideEfficientDAO(
        alcoholDatabase: AlcoholDatabase
    ): EfficientDAO {
        return alcoholDatabase.efficientDao()
    }
}