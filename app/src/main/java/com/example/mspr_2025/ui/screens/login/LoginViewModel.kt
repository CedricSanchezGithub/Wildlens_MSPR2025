package com.example.mspr_2025.ui.screens.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _isAuthenticated = MutableStateFlow(auth.currentUser != null)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val _navigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvent: SharedFlow<LoginNavigationEvent> = _navigationEvent.asSharedFlow()

    fun login(email: String, password: String, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isAuthenticated.value = true
                    emitNavigation(LoginNavigationEvent.NavigateToHome)
                } else {
                    onError(task.exception?.message ?: "Erreur inconnue")
                }
            }
    }

    fun register(email: String, password: String, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isAuthenticated.value = true
                    emitNavigation(LoginNavigationEvent.NavigateToHome)
                } else {
                    onError(task.exception?.message ?: "Erreur inconnue")
                }
            }
    }

    fun onNavigateToRegister() {
        emitNavigation(LoginNavigationEvent.NavigateToRegister)
    }

    fun onNavigateBackToLogin() {
        emitNavigation(LoginNavigationEvent.NavigateBackToLogin)
    }

    private fun emitNavigation(event: LoginNavigationEvent) {
        CoroutineScope(Dispatchers.Main).launch {
            _navigationEvent.emit(event)
        }
    }
}

sealed class LoginNavigationEvent {
    object NavigateToHome : LoginNavigationEvent()
    object NavigateToRegister : LoginNavigationEvent()
    object NavigateBackToLogin : LoginNavigationEvent()
}
