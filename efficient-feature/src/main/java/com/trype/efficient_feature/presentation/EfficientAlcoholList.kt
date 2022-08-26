package com.trype.efficient_feature.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.trype.efficient_feature.data.Alcohol

@Composable
fun EfficientAlcoholList(
    alcoholList: List<Alcohol>,
onAlcoholClicked: (String) -> Unit
) {
    LazyColumn {
        itemsIndexed(
            items = alcoholList,
            key = { _, alcohol -> alcohol.id }
        ) { index, item ->
            Row(
                modifier = Modifier
                    .clickable { onAlcoholClicked(item.url) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.title)
            }
            if (index < alcoholList.lastIndex) {
                Divider()
            }
        }
    }
}