package com.trype.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CategoryRow(onClick: ((name: String) -> Unit), categorySet: Set<String>) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        BubbleFilterButton(name = "Wine", onClick = onClick, set = categorySet)
        BubbleFilterButton(name = "Beer & Cider", onClick = onClick, set = categorySet)
        BubbleFilterButton(name = "Spirits", onClick = onClick, set = categorySet)
        BubbleFilterButton(name = "Coolers", onClick = onClick, set = categorySet)
    }
}