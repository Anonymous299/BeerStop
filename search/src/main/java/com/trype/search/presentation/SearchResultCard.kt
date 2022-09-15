package com.trype.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import com.trype.search.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.trype.core.theme.Colors

@Composable
fun SearchResultCard(value: String, price: Double, image_url: String? = null) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Max).padding(top = 9.dp), backgroundColor = Colors.LightGrey) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            if (image_url == null) {
                Image(
                    painter = painterResource(id = R.drawable.ic_beer_005),
                    contentDescription = "beer bottle",
                    modifier = Modifier
                        .weight(0.25f)
                        .size(width = 54.5.dp, height = 90.83.dp).padding(top = 9.dp, bottom = 9.dp)
                )
            } else {
                AsyncImage(
                    model = image_url,
                    contentDescription = value,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(0.25f)
                        .size(90.83.dp)
                        .padding(top = 9.dp, bottom = 9.dp).clip(CircleShape),
                )
            } //TODO add placeholder


            Column(modifier = Modifier.weight(0.75f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    maxLines = 2,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$$price", maxLines = Integer.MAX_VALUE,
                    style = MaterialTheme.typography.body2, modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}