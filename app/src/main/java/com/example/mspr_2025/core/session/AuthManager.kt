package com.example.mspr_2025.core.session

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthManager {

    private val _isLoggedIn = MutableStateFlow(FirebaseAuth.getInstance().currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        _isLoggedIn.value = firebaseAuth.currentUser != null
    }

    init {
        FirebaseAuth.getInstance().addAuthStateListener(authListener)
    }

    fun removeListener() {
        FirebaseAuth.getInstance().removeAuthStateListener(authListener)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}
