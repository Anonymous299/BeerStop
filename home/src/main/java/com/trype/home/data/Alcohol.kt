package com.trype.home.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity
@JsonClass(generateAdapter = true)
data class Alcohol(
    @PrimaryKey val id: Int,
    val title: String,
    val brand: String,
    val category: String,
    val subcategory: String,
    val price: Double,
    val volume: Double,
    val alcohol_content: Double,
    val price_index: Double,
    val country: String,
    val url: String
)