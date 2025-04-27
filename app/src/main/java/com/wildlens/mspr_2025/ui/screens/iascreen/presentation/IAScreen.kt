package com.wildlens.mspr_2025.ui.screens.iascreen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wildlens.mspr_2025.core.navigation.WildlensNavigationCallbacks
import com.wildlens.mspr_2025.ui.components.WildlensScaffold
import com.wildlens.mspr_2025.ui.screens.iascreen.state.IAState
import com.wildlens.mspr_2025.ui.screens.iascreen.state.IAViewModel

@Composable
fun IAScreen(
    modifier: Modifier = Modifier,
    viewModel: IAViewModel = hiltViewModel(),
    navigationCallbacks: WildlensNavigationCallbacks,
) {
    val uiState by viewModel.uiState.collectAsState()

    WildlensScaffold(navigationCallbacks = navigationCallbacks) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is IAState.Loading -> CircularProgressIndicator()
                is IAState.Success -> IAScreenSuccess(uiModel = state)
                is IAState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
