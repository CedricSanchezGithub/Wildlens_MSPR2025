package com.wildlens.mspr_2025.ui.screens.fav.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavorisViewModel : ViewModel() {

    private val _state = MutableStateFlow<FavorisState>(FavorisState.Loading)
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<FavorisNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<FavorisUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadData()
    }

    fun onAction(action: FavorisAction) {
        when (action) {
            FavorisAction.OnRefresh -> loadData()
            FavorisAction.OnSettingsClicked -> emitNavigation(FavorisNavigationEvent.NavigateToSettings)
            FavorisAction.OnSnackbarRequested -> emitUiEvent(FavorisUiEvent.ShowSnackbar("Snackbar déclenché"))
        }
    }

    private fun loadData() {
        _state.value = FavorisState.Loading
        viewModelScope.launch {
            delay(1000)
            _state.value = FavorisState.Success("Bienvenue depuis Favoris !")
        }
    }

    private fun emitNavigation(event: FavorisNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.emit(event)
        }
    }

    private fun emitUiEvent(event: FavorisUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }
}
