package com.trype.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.trype.core.extensions.collectAsStateWithLifecycle
import com.trype.core.extensions.collectWithLifecycle
import com.trype.core.navigation.NavigationCommand
import com.trype.core.navigation.NavigationManager
import com.trype.core.navigation.SearchNavigation
import com.trype.core.presentation.OutlinedTextFieldNoPadding
import com.trype.home.HomeEvents
import com.trype.home.HomeIntents
import com.trype.home.HomeViewModel
import com.trype.home.R
import kotlinx.coroutines.flow.Flow

//TODO handle loading appropriately
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HandleEvents(events = homeViewModel.event, navigationManager = homeViewModel.navigationManager)
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = uiState.isLoading), onRefresh = {
        homeViewModel.acceptIntent(HomeIntents.RefreshAlcohols)
    }) {
        Column(modifier = Modifier
            .padding(top = 49.dp, start = 34.5.dp)
            .verticalScroll(
                rememberScrollState()
            )) {
            Image(painter = painterResource(id = R.drawable.ic_three_dots_ellipsis),
                contentDescription = "menu")
            Text(text = "Hi, John", style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(top = 21.5.dp, bottom = 20.0.dp))
            Text(text = "Start your search below", style = MaterialTheme.typography.h2)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if(uiState.mostEfficientAlcohol != null)
                    MostEfficientCard(uiState.mostEfficientAlcohol!!.title, uiState.mostEfficientAlcohol!!.thumbnail_url
                    ) {
                        homeViewModel.acceptIntent(HomeIntents.MostEfficientAlcoholClicked(uiState.mostEfficientAlcohol!!))
                    }
                else
                    MostEfficientCard("Jim Bean Black Kentucky", onClick = {}) //TODO handle
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
                singleLine = true,
                paddingValues = PaddingValues(end = 34.5.dp, top = 33.dp)
            )
            Text(text = "Popular search categories", style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 19.dp))
//        Box(modifier = Modifier.padding().align(Alignment.CenterHorizontally)){
            Row(modifier = Modifier
                .padding(top = 15.dp, end = 34.5.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                CategoryCard(title = "Wine", icon = R.drawable.ic_wine_glass, contentDescription = "wine",
                    onClick = {
                        homeViewModel.acceptIntent(HomeIntents.CategoryClicked("Wine"))
                    })
                CategoryCard(title = "Beer & Cider", icon = R.drawable.ic_beer_mug, contentDescription = "beer",
                    onClick = {
                        homeViewModel.acceptIntent(HomeIntents.CategoryClicked("Beer & Cider"))
                    })
                CategoryCard(title = "Spirits", icon = R.drawable.ic_spirit_glass, contentDescription = "spirits",
                    onClick = {
                        homeViewModel.acceptIntent(HomeIntents.CategoryClicked("Spirits"))
                    })
                CategoryCard(title = "Coolers", icon = R.drawable.ic_cooler_can, contentDescription = "coolers",
                    onClick = {
                        homeViewModel.acceptIntent(HomeIntents.CategoryClicked("Coolers"))
                    })
            }
        }
    }
//    }
}

@Composable
private fun HandleEvents(events: Flow<HomeEvents>, navigationManager: NavigationManager) {

    events.collectWithLifecycle {
        when (it) {
            is HomeEvents.OpenCategorySearch -> {
                navigationManager.navigate(it.navigationCommand)
            }
            is HomeEvents.OpenMostEfficientDetails -> {
                navigationManager.navigate(it.navigationCommand)
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}