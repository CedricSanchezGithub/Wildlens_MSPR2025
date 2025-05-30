package com.wildlens.mspr_2025.presentation.screens.auth.state

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}


data class AuthFormState(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val isPasswordVisible: Boolean = false,
    val mode: AuthMode = AuthMode.LOGIN
)
