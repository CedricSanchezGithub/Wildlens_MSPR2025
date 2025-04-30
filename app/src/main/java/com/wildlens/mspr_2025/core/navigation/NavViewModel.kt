package com.wildlens.mspr_2025.core.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.core.session.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val authManager: AuthManager,
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<String>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun onLoginSuccess() {
        viewModelScope.launch {
            _uiEvents.emit("Vous êtes connecté.")
        }
    }

    fun logout() {
        authManager.logout()
        viewModelScope.launch {
            _uiEvents.emit("Vous avez été déconnecté.")
        }
    }
}
