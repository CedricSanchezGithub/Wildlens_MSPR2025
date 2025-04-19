package com.example.mspr_2025.ui.screens.animals.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mspr_2025.ui.components.WildlensScaffold
import com.example.mspr_2025.ui.screens.animals.state.AnimalsState
import com.example.mspr_2025.ui.screens.animals.state.AnimalsViewModel

@Composable
fun AnimalsScreen(
    modifier: Modifier = Modifier,
    viewModel: AnimalsViewModel = hiltViewModel(),
    onHomeClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSettingsClick: () -> Unit = { /* TODO */ },
) {
    val uiState by viewModel.state.collectAsState()

    WildlensScaffold(
        onSettingsClick = onSettingsClick,
        onLoginClick = onLoginClick,
        onHomeClick = onHomeClick,
        onAnimalsClick = { /* TODO */ },
        onProfileClick = { /* TODO */ },
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is AnimalsState.Loading -> CircularProgressIndicator()
                is AnimalsState.Success -> AnimalsScreenSuccess(uiModel = state)
                is AnimalsState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
