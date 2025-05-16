package com.wildlens.mspr_2025.ui.screens.settings.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsState>(SettingsState.Loading)
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    private val _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState: StateFlow<SettingsUiState> = _settingsUiState.asStateFlow()

    init {
        fetchMessage()
    }

    private fun fetchMessage() {
        viewModelScope.launch {
            _uiState.value = SettingsState.Success(_settingsUiState.value)
        }
    }

    fun toggleDarkTheme() {
        _settingsUiState.value = _settingsUiState.value.copy(darkTheme = !_settingsUiState.value.darkTheme)
        _uiState.value = SettingsState.Success(_settingsUiState.value)
    }

    fun toggleHighContrast() {
        _settingsUiState.value = _settingsUiState.value.copy(
            highContrast = !_settingsUiState.value.highContrast
        )
        _uiState.value = SettingsState.Success(_settingsUiState.value)
    }

    fun toggleFontScale() {
        val next = if (_settingsUiState.value.fontScale == FontScale.NORMAL) FontScale.LARGE else FontScale.NORMAL
        _settingsUiState.value = _settingsUiState.value.copy(fontScale = next)
        _uiState.value = SettingsState.Success(_settingsUiState.value)
    }


}