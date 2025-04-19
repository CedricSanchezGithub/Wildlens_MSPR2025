package com.example.mspr_2025.core.navigation

/**
 * Définit les routes disponibles dans l'application.
 * Chaque écran est représenté par un type distinct pour une navigation type-safe.
 */

sealed interface AppRoute {

    object Auth : AppRoute
    object Home : AppRoute
    object Settings : AppRoute
    object Favorites : AppRoute
    object Profile : AppRoute
    object Animals: AppRoute
}
