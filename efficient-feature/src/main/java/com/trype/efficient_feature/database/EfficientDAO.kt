package com.trype.efficient_feature.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trype.efficient_feature.data.Alcohol
import kotlinx.coroutines.flow.Flow

@Dao
interface EfficientDAO {
    @Query("SELECT * FROM alcohol")
    fun getAlcohols(): Flow<List<Alcohol>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAlcohols(rockets: List<Alcohol>)
}