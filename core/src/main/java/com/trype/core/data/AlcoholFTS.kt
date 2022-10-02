package com.trype.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "alcohol_fts")
@Fts4(contentEntity = Alcohol::class)
data class AlcoholFTS(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowid: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "subcategory")
    val subcategory: String
)
