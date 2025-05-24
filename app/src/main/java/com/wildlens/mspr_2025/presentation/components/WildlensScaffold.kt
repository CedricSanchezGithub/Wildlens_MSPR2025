package com.wildlens.mspr_2025.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wildlens.mspr_2025.domain.session.SessionViewModel

@Composable
fun WildlensScaffold(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {

    val sessionViewModel = hiltViewModel<SessionViewModel>()
    val isLoggedIn: State<Boolean> = sessionViewModel.isLoggedIn.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            WildlensTopBar(
                navController = navController,
                isLoggedIn = isLoggedIn.value
            )
        },
        bottomBar = {
            WildlensBottomBar(navController = navController)
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
