package com.trype.efficient_feature.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trype.efficient_feature.data.Alcohol

@Database(entities = [Alcohol::class], version = 1)
abstract class AlcoholDatabase: RoomDatabase(){
    abstract fun efficientDao(): EfficientDAO
}