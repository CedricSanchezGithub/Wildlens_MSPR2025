package com.wildlens.mspr_2025.ui.screens.myscans.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wildlens.mspr_2025.core.navigation.WildlensNavigationCallbacks
import com.wildlens.mspr_2025.ui.components.WildlensScaffold
import com.wildlens.mspr_2025.ui.screens.myscans.state.MyScansState
import com.wildlens.mspr_2025.ui.screens.myscans.state.MyScansViewModel

@Composable
fun MyScansScreen(
    modifier: Modifier = Modifier,
    viewModel: MyScansViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigationCallbacks: WildlensNavigationCallbacks,
) {
    val uiState by viewModel.uiState.collectAsState()

    WildlensScaffold(
        navigationCallbacks = navigationCallbacks,
        snackbarHostState = snackbarHostState
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is MyScansState.Loading -> CircularProgressIndicator()
                is MyScansState.Success -> MyScansScreenSuccess(uiModel = state)
                is MyScansState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
