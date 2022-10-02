package com.trype.search.presentation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SubcategoryRow(subcategoryList: List<String>, onClick: ((name: String) -> Unit),
subcategorySet: Set<String>) {
    LazyRow {
        items(items = subcategoryList, itemContent = { subcategory ->
            BubbleFilterButton(name = subcategory, onClick = onClick, set = subcategorySet,
                modifier = Modifier.padding(end = 30.dp))
        })
    }
}