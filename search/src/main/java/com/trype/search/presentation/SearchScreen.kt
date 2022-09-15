package com.trype.search.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trype.core.extensions.collectAsStateWithLifecycle
import com.trype.core.presentation.OutlinedTextFieldNoPadding
import com.trype.search.R
import com.trype.search.SearchIntents
import com.trype.search.SearchViewModel

@Composable
fun SearchScreen(searchViewModel: SearchViewModel = hiltViewModel(), category: String?) {
    Log.d("SahilTest", "category: $category")
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    searchViewModel.acceptIntent(SearchIntents.GetSpirits(category ?: "Spirits"))
    Column(modifier = Modifier.padding(top = 35.5.dp, start = 29.5.dp, end = 22.5.dp)) {
        Image(painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "filter",
        modifier = Modifier.scale(0.6f))
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
        LazyColumn(modifier = Modifier.padding(top = 15.dp).fillMaxHeight()) {
            items(items = uiState.searchResults, itemContent = { alcohol ->
                SearchResultCard(alcohol.title, alcohol.price, alcohol.image_url)
            })
        }
    }
}