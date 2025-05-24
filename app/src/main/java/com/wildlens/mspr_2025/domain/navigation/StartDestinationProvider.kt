package com.wildlens.mspr_2025.domain.navigation

import javax.inject.Inject

/**
 * Fournisseur du point d'entrée initial de l'application.
 * Peut être enrichi avec des préférences locales, une session, etc.
 */

class StartDestinationProvider @Inject constructor() {
    fun getStartDestination(): String {
        return AppRoute.Home.route
    }
}
