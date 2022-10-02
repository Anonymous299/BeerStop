package com.trype.search.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.trype.core.theme.Colors
import com.trype.search.SearchIntents

@Composable
fun BubbleFilterButton(name: String, onClick: ((name: String) -> Unit), set: Set<String>,
modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = {
                  onClick(name)
        },
        border = if(set.contains(name)) {
            BorderStroke(1.dp, Color.Green)
        }
        else{
            BorderStroke(1.dp, Colors.LightGrey)
        },
        shape = RoundedCornerShape(50),
        modifier = modifier
    ){
        Text( text = name , style = MaterialTheme.typography.h3)
    }
}