package com.trype.core.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.trype.core.data.Alcohol
import kotlinx.coroutines.flow.Flow

@Dao
interface AlcoholDAO {
    @Query("SELECT * FROM alcohol")
    fun getAlcohols(): Flow<List<Alcohol>>

    @Query("SELECT * FROM alcohol WHERE price_index=(SELECT MIN(price_index) FROM alcohol)")
    fun getMostEfficientAlcohol(): Flow<Alcohol?>

    @Query("SELECT * FROM alcohol WHERE rowid=:id")
    fun getAlcoholFromId(id: Int): Flow<Alcohol>

    @Query("SELECT * FROM alcohol WHERE (category IN(:categorySet))")
    fun getSpirits(categorySet: Set<String>): Flow<List<Alcohol>>

    @Query("SELECT subcategory FROM alcohol WHERE (category IN(:categorySet))")
    fun getSubcategories(categorySet: Set<String>): Flow<List<String>>

    @RawQuery(observedEntities = [Alcohol::class])
    fun getCategoryViaQuery(query: SupportSQLiteQuery): Flow<List<Alcohol>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAlcohols(rockets: List<Alcohol>)
}