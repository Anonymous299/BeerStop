package com.trype.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trype.search.R

@Composable
fun SearchScreen() {
    Column(modifier = Modifier.padding(top = 35.5.dp, start = 29.5.dp)) {
        Image(painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "filter")
    }
}