package com.example.mspr_2025.ui.screens.fav.state

sealed interface FavorisUiEvent {
    data class ShowSnackbar(val message: String) : FavorisUiEvent
    object FocusSearchBar : FavorisUiEvent
}
