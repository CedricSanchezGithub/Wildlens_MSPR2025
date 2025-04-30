package com.wildlens.mspr_2025.ui.screens.profile.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.wildlens.mspr_2025.ui.components.WildlensScaffold
import com.wildlens.mspr_2025.ui.screens.profile.state.ProfileState
import com.wildlens.mspr_2025.ui.screens.profile.state.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    WildlensScaffold(navController = navController) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is ProfileState.Loading -> CircularProgressIndicator()
                is ProfileState.Success -> ProfileScreenSuccess(uiModel = state)
                is ProfileState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
