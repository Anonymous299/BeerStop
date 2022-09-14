package com.trype.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.trype.core.extensions.collectAsStateWithLifecycle
import com.trype.core.theme.Raleway
import com.trype.home.R

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.padding(top = 49.dp, start = 34.5.dp)) {
        Image(painter = painterResource(id = R.drawable.ic_three_dots_ellipsis),
            contentDescription = "menu")
        Text(text = "Hi, John", style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(top = 21.5.dp, bottom = 20.0.dp))
        Text(text = "Start your search below", style = MaterialTheme.typography.h2)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            MostEfficientCard()
        }
        OutlinedTextFieldNoPadding(value = "Search",
            onValueChange = {},
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            singleLine = true
        )
        Text(text = "Popular search categories", style = MaterialTheme.typography.h2,
        modifier = Modifier.padding(top = 19.dp))
//        Box(modifier = Modifier.padding().align(Alignment.CenterHorizontally)){
            Row(modifier = Modifier.padding(top = 15.dp, end = 34.5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CategoryCard(title = "Wine", icon = R.drawable.ic_wine_glass, contentDescription = "wine")
                CategoryCard(title = "Beer & Cider", icon = R.drawable.ic_beer_mug, contentDescription = "beer")
                CategoryCard(title = "Spirits", icon = R.drawable.ic_spirit_glass, contentDescription = "spirits")
                CategoryCard(title = "Coolers", icon = R.drawable.ic_cooler_can, contentDescription = "coolers")
            }
        }
//    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}