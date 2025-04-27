package com.wildlens.mspr_2025.ui.screens.iascreen.state

sealed interface IAAction {
    object OnRefresh : IAAction
    object OnSettingsClicked : IAAction
    object OnSnackbarRequested : IAAction
}
