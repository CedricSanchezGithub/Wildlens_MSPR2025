package com.wildlens.mspr_2025.ui.screens.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wildlens.mspr_2025.core.events.UiEvent
import com.wildlens.mspr_2025.core.session.SessionViewModel
import com.wildlens.mspr_2025.ui.components.WildlensScaffold
import com.wildlens.mspr_2025.ui.screens.home.state.HomeState
import com.wildlens.mspr_2025.ui.screens.home.state.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val sessionViewModel: SessionViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        sessionViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                is UiEvent.Navigate -> navController.navigate(event.route) {
                    if (event.inclusive) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
                else -> {}
            }
        }
    }


    WildlensScaffold(
        snackbarHostState = snackbarHostState,
        navController = navController,
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is HomeState.Loading -> CircularProgressIndicator()
                is HomeState.Success<String> -> HomeScreenSuccess(navController)
                is HomeState.Error -> Text(state.message)
            }
        }
    }
}
