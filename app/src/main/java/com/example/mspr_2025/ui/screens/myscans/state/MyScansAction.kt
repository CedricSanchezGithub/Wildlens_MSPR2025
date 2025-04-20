package com.example.mspr_2025.ui.screens.myscans.state

sealed interface MyScansAction {
    object OnRefresh : MyScansAction
    object OnSettingsClicked : MyScansAction
    object OnSnackbarRequested : MyScansAction
}
