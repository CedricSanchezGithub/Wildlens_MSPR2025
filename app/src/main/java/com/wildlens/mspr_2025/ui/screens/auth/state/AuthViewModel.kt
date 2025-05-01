package com.wildlens.mspr_2025.ui.screens.auth.state

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wildlens.mspr_2025.core.events.BaseViewModel
import com.wildlens.mspr_2025.core.events.UiEvent
import com.wildlens.mspr_2025.data.models.UserDataModel
import com.wildlens.mspr_2025.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _form = MutableStateFlow(AuthFormState())
    val form: StateFlow<AuthFormState> = _form.asStateFlow()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AuthUiEvent>()
    val events: SharedFlow<AuthUiEvent> = _events.asSharedFlow()

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

        viewModelScope.launch {
            try {
                val authResult = if (formValue.mode == AuthMode.LOGIN) {
                    auth.signInWithEmailAndPassword(formValue.email, formValue.password).await()
                } else {
                    auth.createUserWithEmailAndPassword(formValue.email, formValue.password).await()
                }

                val uid = authResult.user?.uid

                if (formValue.mode == AuthMode.REGISTER && uid != null) {
                    val user = UserDataModel(
                        uid = uid,
                        firstName = formValue.firstName,
                        lastName = formValue.lastName,
                        email = formValue.email
                    )
                    userRepository.createUser(user)
                }

                _uiState.value = AuthUiState.Loading
                sendUiEvent(UiEvent.ShowSnackbar("Connexion réussie"))
                sendUiEvent(UiEvent.Navigate("home", inclusive = true))

            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Erreur inconnue")
            }
        }
    }
}


