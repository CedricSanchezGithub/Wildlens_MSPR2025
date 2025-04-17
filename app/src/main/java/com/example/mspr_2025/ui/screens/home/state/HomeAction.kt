package com.example.mspr_2025.ui.screens.home.state

sealed interface HomeAction {
    object OnRefresh : HomeAction
    object OnSettingsClicked : HomeAction
    object OnSnackbarRequested : HomeAction
}
