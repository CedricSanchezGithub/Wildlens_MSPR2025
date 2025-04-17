package com.example.mspr_2025.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mspr_2025.ui.composables.WildlensScaffold
import com.example.mspr_2025.ui.screens.home.state.HomeState
import com.example.mspr_2025.ui.screens.home.state.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    WildlensScaffold {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState.value) {
                is HomeState.Loading -> CircularProgressIndicator()
                is HomeState.Success -> HomeScreenSuccess(text = state.data)
                is HomeState.Error -> Text(text = state.message)
            }
        }
    }
}
