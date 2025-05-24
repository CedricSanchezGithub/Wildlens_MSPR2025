package com.wildlens.mspr_2025.domain.session

import com.google.firebase.auth.FirebaseUser
import com.wildlens.mspr_2025.domain.events.BaseViewModel
import com.wildlens.mspr_2025.domain.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    val isLoggedIn: StateFlow<Boolean> = sessionManager.isLoggedIn

    fun logout() {
        sessionManager.logout()
        sendUiEvent(UiEvent.ShowSnackbar("Déconnexion réussie"))
        sendUiEvent(UiEvent.Navigate("home", inclusive = true))
    }

    val currentUser: FirebaseUser?
        get() = sessionManager.currentUser
}