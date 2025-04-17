package com.example.mspr_2025.ui.screens.fav.state

sealed interface FavorisAction {
    object OnRefresh : FavorisAction
    object OnSettingsClicked : FavorisAction
    object OnSnackbarRequested : FavorisAction
}
