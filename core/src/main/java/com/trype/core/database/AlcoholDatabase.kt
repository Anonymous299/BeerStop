package com.trype.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trype.core.data.Alcohol

@Database(entities = [Alcohol::class], version = 1)
abstract class AlcoholDatabase: RoomDatabase(){
    abstract fun alcoholDao(): AlcoholDAO
}