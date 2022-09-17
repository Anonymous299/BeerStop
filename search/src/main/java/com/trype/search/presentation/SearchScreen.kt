package com.trype.search.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trype.core.extensions.collectAsStateWithLifecycle
import com.trype.core.extensions.collectWithLifecycle
import com.trype.core.navigation.NavigationManager
import com.trype.core.presentation.OutlinedTextFieldNoPadding
import com.trype.core.theme.Colors
import com.trype.search.R
import com.trype.search.SearchEvents
import com.trype.search.SearchIntents
import com.trype.search.SearchViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel(), category: String?) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    HandleEvents(events = searchViewModel.event, navigationManager = searchViewModel.navigationManager)
    //TODO change logic when more queries are added
    if (category == null){
        if(uiState.category.isNotEmpty())
            searchViewModel.acceptIntent(SearchIntents.SaveCategory(uiState.category))
    }
    else {
        val categorySet = HashSet<String>()
        categorySet.addAll(uiState.category)
        categorySet.add(category)
        searchViewModel.acceptIntent(SearchIntents.SaveCategory(categorySet))
    }
    val searchResults by searchViewModel.searchResults.collectAsStateWithLifecycle()
    Column(modifier = Modifier.padding(top = 35.5.dp, start = 29.5.dp, end = 22.5.dp)) {
        Image(painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "filter",
        modifier = Modifier
            .scale(0.6f)
            .clickable {
                Log.d("SahilTest", "filter state: ${uiState.filter}")
                searchViewModel.acceptIntent(SearchIntents.ToggleFilter(!uiState.filter))
            })
        if(uiState.filter){
            OutlinedButton(
    onClick = { },
        border = BorderStroke(1.dp, Colors.LightGrey),
    shape = RoundedCornerShape(50), // = 50% percent
    // or shape = CircleShape
    colors = ButtonDefaults.outlinedButtonColors(contentColor = Colors.LightGrey)
){
    Text( text = "Wine" )
}
        }
        OutlinedTextFieldNoPadding(value = "Spirits",
            onValueChange = {},
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            singleLine = true,
            paddingValues = PaddingValues(top = 21.5.dp, end = 12.dp)
        )
        Text(text = "Showing results for Spirits...", style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(top = 17.5.dp))
        LazyColumn(modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxHeight()) {
            items(items = searchResults, itemContent = { alcohol ->
                SearchResultCard(alcohol.title, alcohol.price, alcohol.image_url){
                    searchViewModel.acceptIntent(SearchIntents.AlcoholClicked(alcohol))
                }
            })
        }
    }
}

@Composable
private fun HandleEvents(events: Flow<SearchEvents>, navigationManager: NavigationManager) {

    events.collectWithLifecycle {
        when (it) {
            is SearchEvents.OpenAlcoholDetails -> {
                navigationManager.navigate(it.navigationCommand)
            }
        }
    }
}