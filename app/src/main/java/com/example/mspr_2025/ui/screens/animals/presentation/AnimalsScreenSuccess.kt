package com.example.mspr_2025.ui.screens.animals.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mspr_2025.ui.screens.animals.state.AnimalsState

@Composable
fun AnimalsScreenSuccess(uiModel: AnimalsState.Success) {
    Text(text = uiModel.message)
}
