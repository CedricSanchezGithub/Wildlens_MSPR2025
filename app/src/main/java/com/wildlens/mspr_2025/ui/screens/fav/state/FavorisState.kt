package com.wildlens.mspr_2025.ui.screens.fav.state

sealed class FavorisState {
    object Loading : FavorisState()
    data class Success(val message: String) : FavorisState()
    data class Error(val message: String) : FavorisState()
}
