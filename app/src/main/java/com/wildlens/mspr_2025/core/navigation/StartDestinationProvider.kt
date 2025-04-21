package com.wildlens.mspr_2025.core.navigation

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Fournisseur du point d'entrée initial de l'application.
 * Peut être enrichi avec des préférences locales, une session, etc.
 */

class StartDestinationProvider @Inject constructor() {
    fun getStartDestination(): AppRoute {
        val user = FirebaseAuth.getInstance().currentUser
        return if (user != null) AppRoute.Home else AppRoute.Auth
    }
}
