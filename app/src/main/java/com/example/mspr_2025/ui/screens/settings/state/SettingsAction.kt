package com.example.mspr_2025.ui.screens.settings.state

sealed interface SettingsAction {
    object OnRefresh : SettingsAction
    object OnSettingsClicked : SettingsAction
    object OnSnackbarRequested : SettingsAction
}
