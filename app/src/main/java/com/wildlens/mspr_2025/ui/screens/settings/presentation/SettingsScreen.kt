package com.wildlens.mspr_2025.ui.screens.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wildlens.mspr_2025.core.navigation.WildlensNavigationCallbacks
import com.wildlens.mspr_2025.ui.components.WildlensScaffold
import com.wildlens.mspr_2025.ui.screens.settings.state.SettingsState
import com.wildlens.mspr_2025.ui.screens.settings.state.SettingsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    navigationCallbacks: WildlensNavigationCallbacks,
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    WildlensScaffold(
        snackbarHostState = remember { SnackbarHostState() },
        navigationCallbacks = navigationCallbacks
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is SettingsState.Loading -> CircularProgressIndicator()
                is SettingsState.Success -> SettingsScreenSuccess(uiModel = state)
                is SettingsState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
