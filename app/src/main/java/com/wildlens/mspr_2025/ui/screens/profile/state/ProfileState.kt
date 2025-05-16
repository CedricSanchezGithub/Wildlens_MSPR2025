package com.wildlens.mspr_2025.ui.screens.profile.state

sealed class ProfileState {
    data object Loading : ProfileState()
    data class Success(val message: String) : ProfileState()
    data class Error(val message: String) : ProfileState()
}
