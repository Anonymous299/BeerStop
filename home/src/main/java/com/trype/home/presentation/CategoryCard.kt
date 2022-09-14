package com.trype.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trype.core.theme.Colors

@Composable
fun CategoryCard(title: String, icon: Int, contentDescription: String) {
    Card(modifier = Modifier
        .width(70.dp)
        .height(90.dp)
        .padding(end = 3.5.dp), backgroundColor = Colors.LightGrey) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Icon(painter = painterResource(id = icon),
                contentDescription = contentDescription,
            modifier = Modifier.weight(0.75f).scale(0.6f))
            Text(text = title, modifier = Modifier.weight(0.25f),
                style = MaterialTheme.typography.body1)
        }
    }
}