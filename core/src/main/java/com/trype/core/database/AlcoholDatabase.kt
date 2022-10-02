package com.trype.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trype.core.data.Alcohol
import com.trype.core.data.AlcoholFTS

@Database(entities = [Alcohol::class, AlcoholFTS::class], exportSchema = false, version = 2)
abstract class AlcoholDatabase: RoomDatabase(){
    abstract fun alcoholDao(): AlcoholDAO
}