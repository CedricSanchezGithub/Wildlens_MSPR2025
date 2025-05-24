package com.wildlens.mspr_2025.domain.navigation

/**
 * Définit les routes disponibles dans l'application.
 * Chaque écran est représenté par un type distinct pour une navigation type-safe.
 */

sealed class AppRoute(val route: String) {
    object Auth : AppRoute("auth")
    object Home : AppRoute("home")
    object Settings : AppRoute("settings")
    object Profile : AppRoute("profile")
    object Animals : AppRoute("animals")
    object MyScans : AppRoute("my_scans")
    object IA : AppRoute("ia")
    object Camera : AppRoute("camera")
}