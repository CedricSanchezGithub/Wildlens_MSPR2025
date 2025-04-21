package com.wildlens.mspr_2025.ui.screens.auth.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.models.UserDataModel
import com.wildlens.mspr_2025.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _form = MutableStateFlow(AuthFormState())
    val form: StateFlow<AuthFormState> = _form

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.EmailChanged -> _form.update { it.copy(email = action.email) }
            is AuthAction.PasswordChanged -> _form.update { it.copy(password = action.password) }
            is AuthAction.FirstNameChanged -> _form.update { it.copy(firstName = action.firstName) }
            is AuthAction.LastNameChanged -> _form.update { it.copy(lastName = action.lastName) }
            is AuthAction.SwitchMode -> _form.update { it.copy(mode = action.mode) }
            is AuthAction.TogglePasswordVisibility ->
                _form.update { it.copy(isPasswordVisible = !_form.value.isPasswordVisible) }
            is AuthAction.Submit -> submit()
        }
    }


    private fun submit() {
        val formValue = _form.value
        _uiState.value = AuthUiState.Loading

        val task = if (formValue.mode == AuthMode.LOGIN) {
            auth.signInWithEmailAndPassword(formValue.email, formValue.password)
        } else {
            auth.createUserWithEmailAndPassword(formValue.email, formValue.password)
        }

        task.addOnCompleteListener { result ->
            if (result.isSuccessful) {
                val uid = auth.currentUser?.uid

                if (formValue.mode == AuthMode.REGISTER && uid != null) {
                    val user = UserDataModel(
                        uid = uid,
                        firstName = formValue.firstName,
                        lastName = formValue.lastName,
                        email = formValue.email
                    )

                    viewModelScope.launch {
                        userRepository.createUser(user)
                        _uiState.value = AuthUiState.Success
                    }
                } else {
                    _uiState.value = AuthUiState.Success
                }
            } else {
                _uiState.value = AuthUiState.Error(result.exception?.message ?: "Erreur inconnue")
            }
        }
    }
}
