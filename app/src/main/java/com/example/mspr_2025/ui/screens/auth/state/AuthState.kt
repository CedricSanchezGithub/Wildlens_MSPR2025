package com.example.mspr_2025.ui.screens.auth.state

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}


data class AuthFormState(
    val email: String = "",
    val password: String = "",
    val mode: AuthMode = AuthMode.LOGIN,
    val isPasswordVisible: Boolean = false
)
