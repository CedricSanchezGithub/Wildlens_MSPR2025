package com.example.mspr_2025.ui.screens.auth.state

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _form = MutableStateFlow(AuthFormState())
    val form: StateFlow<AuthFormState> = _form

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.EmailChanged -> _form.update { it.copy(email = action.email) }
            is AuthAction.PasswordChanged -> _form.update { it.copy(password = action.password) }
            is AuthAction.SwitchMode -> _form.update { it.copy(mode = action.mode) }
            is AuthAction.TogglePasswordVisibility ->
                _form.update { it.copy(isPasswordVisible = !_form.value.isPasswordVisible) }
            is AuthAction.Submit -> {
                submit()
            }
        }
    }

    private fun submit() {
        val (email, password, mode) = _form.value
        _uiState.value = AuthUiState.Loading

        val task = if (mode == AuthMode.LOGIN) {
            auth.signInWithEmailAndPassword(email, password)
        } else {
            auth.createUserWithEmailAndPassword(email, password)
        }

        task.addOnCompleteListener { result ->
            _uiState.value = if (result.isSuccessful) {
                AuthUiState.Success
            } else {
                AuthUiState.Error(result.exception?.message ?: "Erreur inconnue")
            }
        }
    }
}
