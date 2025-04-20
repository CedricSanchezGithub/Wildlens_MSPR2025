package com.example.mspr_2025.ui.screens.settings.state

sealed class SettingsState {
    object Loading : SettingsState()
    data class Success(val message: String) : SettingsState()
    data class Error(val message: String) : SettingsState()
}
