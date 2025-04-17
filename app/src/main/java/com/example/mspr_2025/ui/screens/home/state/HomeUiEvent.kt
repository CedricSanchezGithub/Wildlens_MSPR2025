package com.example.mspr_2025.ui.screens.home.state

sealed interface HomeUiEvent {
    data class ShowSnackbar(val message: String) : HomeUiEvent
    object FocusSearchBar : HomeUiEvent
}
