package com.wildlens.mspr_2025.ui.screens.iascreen.state

sealed class IAState {
    object Loading : IAState()
    data class Success(val message: String) : IAState()
    data class Error(val message: String) : IAState()
}
