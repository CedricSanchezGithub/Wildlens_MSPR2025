package com.example.mspr_2025.ui.screens.settings.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mspr_2025.ui.screens.settings.state.SettingsState

@Composable
fun SettingsScreenSuccess(uiModel: SettingsState.Success) {
    Text(text = uiModel.message)
}
