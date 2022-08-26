package com.trype.efficient_feature.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.trype.efficient_feature.data.Alcohol

@Composable
fun EfficientAlcoholList(
    alcoholList: List<Alcohol>
) {
    LazyColumn {
        itemsIndexed(
            items = alcoholList,
            key = { _, alcohol -> alcohol.id }
        ) { index, item ->
            Text(text = item.title)

            if (index < alcoholList.lastIndex) {
                Divider()
            }
        }
    }
}