package com.wildlens.mspr_2025.ui.screens.animals.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wildlens.mspr_2025.ui.components.WildlensScaffold
import com.wildlens.mspr_2025.ui.screens.animals.state.AnimalsState
import com.wildlens.mspr_2025.ui.screens.animals.state.AnimalsViewModel
import androidx.compose.runtime.getValue
import com.wildlens.mspr_2025.core.navigation.WildlensNavigationCallbacks

@Composable
fun AnimalsScreen(
    modifier: Modifier = Modifier,
    viewModel: AnimalsViewModel = hiltViewModel(),
    navigationCallbacks: WildlensNavigationCallbacks,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    WildlensScaffold(
        snackbarHostState = snackbarHostState,
        navigationCallbacks = navigationCallbacks
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is AnimalsState.Loading -> CircularProgressIndicator()
                is AnimalsState.Success -> AnimalsScreenSuccess(state.animalDataModels)
                is AnimalsState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}