package com.trype.search.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel(), category: String?) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    HandleEvents(events = searchViewModel.event, navigationManager = searchViewModel.navigationManager)
    val searchResults by searchViewModel.searchResults.collectAsStateWithLifecycle(emptyList())
    val subcategoryList by searchViewModel.subCategoryResults.collectAsStateWithLifecycle(emptyList())

    var searchText by remember {
        mutableStateOf("")
    }
//    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Column(modifier = Modifier.padding(top = 35.5.dp, start = 29.5.dp, end = 22.5.dp)) {

        Image(painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "filter",
        modifier = Modifier
            .scale(0.6f)
            .clickable {
                searchViewModel.acceptIntent(SearchIntents.ToggleFilter)
            }
            )
        if(uiState.showFilter){
            CategoryRow(onClick = { category ->
               searchViewModel.acceptIntent(SearchIntents.ChangeCategory(category))
            }, categorySet = uiState.category)
            SubcategoryRow(onClick = { subcategory ->
                searchViewModel.acceptIntent(SearchIntents.ChangeSubcategory(subcategory))
            }, subcategoryList = subcategoryList, subcategorySet = uiState.subcategory)
        }
        OutlinedTextFieldNoPadding(value = uiState.title,
            onValueChange = { title ->
                            searchViewModel.acceptIntent(SearchIntents.ChangeTitle(title))
            },
            leadingIcon = {
                IconButton(onClick = {
                    searchViewModel.acceptIntent(SearchIntents.ChangeTitle(searchText))
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "search")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .focusRequester(focusRequester),
            singleLine = true,
            paddingValues = PaddingValues(top = 21.5.dp, end = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            maxLines = 1
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