package com.example.mspr_2025.ui.screens.auth.state

sealed interface AuthAction {
    data class EmailChanged(val email: String) : AuthAction
    data class PasswordChanged(val password: String) : AuthAction
    data class Submit(val mode: AuthMode) : AuthAction
    data class SwitchMode(val mode: AuthMode) : AuthAction
    data class FirstNameChanged(val firstName: String) : AuthAction
    data class LastNameChanged(val lastName: String) : AuthAction
    object TogglePasswordVisibility : AuthAction
}
