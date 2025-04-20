package com.example.mspr_2025.ui.screens.myscans.state

sealed interface MyScansUiEvent {
    data class ShowSnackbar(val message: String) : MyScansUiEvent
    object FocusSearchBar : MyScansUiEvent
}
