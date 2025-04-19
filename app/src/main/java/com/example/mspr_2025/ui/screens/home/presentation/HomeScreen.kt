package com.example.mspr_2025.ui.screens.home.presentation

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
import com.example.mspr_2025.core.navigation.NavViewModel
import com.example.mspr_2025.ui.components.WildlensScaffold
import com.example.mspr_2025.ui.screens.home.state.HomeState
import com.example.mspr_2025.ui.screens.home.state.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navViewModel: NavViewModel = hiltViewModel(),
    onSettingsClick: () -> Unit,
    onLoginClick: () -> Unit,
    onHomeClick: () -> Unit,
    onAnimalsClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        navViewModel.uiEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    WildlensScaffold(
        onSettingsClick = onSettingsClick,
        onLoginClick = onLoginClick,
        onHomeClick = onHomeClick,
        onAnimalsClick = onAnimalsClick,
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
                is HomeState.Success<String> -> HomeScreenSuccess(text = state.data)
                is HomeState.Error -> Text(state.message)
            }
        }
    }
}
