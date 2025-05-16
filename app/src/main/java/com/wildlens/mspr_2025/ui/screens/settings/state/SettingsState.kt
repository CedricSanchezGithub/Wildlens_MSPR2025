package com.wildlens.mspr_2025.ui.screens.settings.state

sealed class SettingsState {
    data object Loading : SettingsState()
    data class Success(val settings: SettingsUiState) : SettingsState()
    data class Error(val message: String) : SettingsState()
}

data class SettingsUiState(
    val darkTheme: Boolean = false,
    val highContrast: Boolean = false,
    val fontScale: FontScale = FontScale.NORMAL
)

enum class FontScale(val scale: Float) {
    NORMAL(1.0f),
    LARGE(1.3f)
}
