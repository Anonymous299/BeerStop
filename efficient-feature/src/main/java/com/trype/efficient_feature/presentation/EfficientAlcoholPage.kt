package com.trype.efficient_feature.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.trype.core.extensions.collectAsStateWithLifecycle
import com.trype.core.extensions.collectWithLifecycle
import com.trype.efficient_feature.*
import kotlinx.coroutines.flow.Flow

@Composable
fun EfficientAlcoholPage(
    viewModel: EfficientAlcoholViewModel = hiltViewModel()
) {
    HandleEvents(events = viewModel.event)

    val scaffoldState = rememberScaffoldState()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = {
                viewModel.acceptIntent(EfficientAlcoholIntents.RefreshAlcohols)
            },
            modifier = Modifier.padding(padding)
        ) {
            if (uiState.alcoholList.isNotEmpty()) {
                AlcoholAvailableContent(
                    scaffoldState = scaffoldState,
                    uiState = uiState
                ) { uri ->
                    viewModel.acceptIntent(EfficientAlcoholIntents.AlcoholClicked(uri))
                }
            } else {
                AlcoholNotAvailableContent(
                    uiState = uiState
                )
            }
        }
    }

}

@Composable
private fun HandleEvents(events: Flow<EfficientAlcoholEvents>) {
    val uriHandler = LocalUriHandler.current

    //Collects events as long as activity is at least started
    events.collectWithLifecycle {
        when (it) {
            is EfficientAlcoholEvents.OpenAlcoholInWebBrowser -> {
                uriHandler.openUri(it.uri)
            }
        }
    }
}

@Composable
private fun AlcoholAvailableContent(
    scaffoldState: ScaffoldState,
    uiState: EfficientAlcoholUIState,
    onAlcoholClicked: (String) -> Unit
) {
    if (uiState.isError) {
        val errorMessage = stringResource(R.string.alcohol_error_message)

        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessage
            )
        }
    }

    EfficientAlcoholList(
        alcoholList = uiState.alcoholList,
        onAlcoholClicked
    )
}

@Composable
private fun AlcoholNotAvailableContent(uiState: EfficientAlcoholUIState) {
    when {
        uiState.isLoading -> AlcoholLoadingPlaceholder()
        uiState.isError -> AlcoholErrorContent()
    }
}