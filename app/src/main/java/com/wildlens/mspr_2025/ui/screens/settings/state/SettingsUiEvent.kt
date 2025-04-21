package com.wildlens.mspr_2025.ui.screens.settings.state

sealed interface SettingsUiEvent {
    data class ShowSnackbar(val message: String) : SettingsUiEvent
    object FocusSearchBar : SettingsUiEvent
}
