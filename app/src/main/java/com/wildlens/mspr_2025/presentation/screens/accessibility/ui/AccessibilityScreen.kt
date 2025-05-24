package com.wildlens.mspr_2025.presentation.screens.accessibility.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wildlens.mspr_2025.presentation.components.WildlensScaffold
import com.wildlens.mspr_2025.presentation.screens.accessibility.state.SettingsState
import com.wildlens.mspr_2025.presentation.screens.accessibility.state.AccessibilityViewModel

@Composable
fun AccessibilityScreen(
    modifier: Modifier = Modifier,
    viewModel: AccessibilityViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    WildlensScaffold(
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
                is SettingsState.Loading -> CircularProgressIndicator()
                is SettingsState.Success -> AccessibilityScreenSuccess(
                    viewModel = viewModel,
                    settings = state.settings
                )
                is SettingsState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
