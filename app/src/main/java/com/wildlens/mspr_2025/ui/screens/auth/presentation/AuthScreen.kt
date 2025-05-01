package com.wildlens.mspr_2025.ui.screens.auth.presentation

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
import com.wildlens.mspr_2025.ui.components.WildlensScaffold
import com.wildlens.mspr_2025.ui.screens.auth.state.AuthUiState
import com.wildlens.mspr_2025.ui.screens.auth.state.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val formState by viewModel.form.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val viewModel: AuthViewModel = hiltViewModel()
    val uiEvent = viewModel.uiEvent
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                is UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        if (event.inclusive) {
                            popUpTo(navController.currentDestination?.route ?: "") {
                                inclusive = true
                            }
                        }
                    }
                }
                is UiEvent.PopBackStack -> navController.popBackStack()
            }
        }
    }

    WildlensScaffold(
        snackbarHostState = snackbarHostState,
        navController = navController,
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                AuthUiState.Loading -> CircularProgressIndicator()
                is AuthUiState.Error -> {
                    Text((uiState as AuthUiState.Error).message)
                }
                else -> AuthScreenSuccess(formState, viewModel::onAction)
            }
        }
    }
}
