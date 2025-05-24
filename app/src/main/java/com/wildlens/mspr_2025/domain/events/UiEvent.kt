package com.wildlens.mspr_2025.domain.events

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    data class Navigate(val route: String, val inclusive: Boolean = false) : UiEvent()
    object PopBackStack : UiEvent()
}
