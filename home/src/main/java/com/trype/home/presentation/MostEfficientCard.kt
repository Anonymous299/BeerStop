package com.trype.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trype.core.theme.Colors
import com.trype.home.R

@Composable
fun MostEfficientCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 34.5.dp, top = 15.dp), backgroundColor = Colors.LightGrey) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
            Column(modifier = Modifier.padding(start = 33.dp, top = 37.dp).weight(0.5f)) {
                Text(text = "Jim Bean Black Kentucky", maxLines = Integer.MAX_VALUE, style = MaterialTheme.typography.h1)
                Text(text = "The LCBO's most cost-efficient product", maxLines = Integer.MAX_VALUE,
                    style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 10.dp))
            }
            Image(painter = painterResource(id = R.drawable.ic_beer_005),
                contentDescription = "beer bottle",
                modifier = Modifier
                    .weight(0.5f)
                    .size(width = 54.5.dp, height = 150.dp)
                    .padding(top = 20.dp))
        }
    }
}
