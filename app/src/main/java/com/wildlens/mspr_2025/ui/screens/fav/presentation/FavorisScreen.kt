package com.wildlens.mspr_2025.ui.screens.fav.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wildlens.mspr_2025.ui.screens.fav.state.FavorisState
import com.wildlens.mspr_2025.ui.screens.fav.state.FavorisUiEvent
import com.wildlens.mspr_2025.ui.screens.fav.state.FavorisViewModel

@Composable
fun FavorisScreen(
    modifier: Modifier = Modifier,
    viewModel: FavorisViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is FavorisUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                FavorisUiEvent.FocusSearchBar -> {
                    // À implémenter si nécessaire
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is FavorisState.Loading -> CircularProgressIndicator()
                is FavorisState.Success -> FavorisScreenSuccess(uiModel = state)
                is FavorisState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
