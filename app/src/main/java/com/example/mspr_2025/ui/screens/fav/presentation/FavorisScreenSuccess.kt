package com.example.mspr_2025.ui.screens.fav.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mspr_2025.ui.screens.fav.state.FavorisState

@Composable
fun FavorisScreenSuccess(uiModel: FavorisState.Success) {
    Text(text = uiModel.message)
}
