package com.trype.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trype.core.data.Alcohol
import kotlinx.coroutines.flow.Flow

@Dao
interface AlcoholDAO {
    @Query("SELECT * FROM alcohol")
    fun getAlcohols(): Flow<List<Alcohol>>

    @Query("SELECT * FROM alcohol WHERE price=(SELECT MIN(price) FROM alcohol)")
    fun getMostEfficientAlcohol(): Flow<Alcohol?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAlcohols(rockets: List<Alcohol>)
}