package com.wildlens.mspr_2025.core.navigation

/**
 * Centralise tous les callbacks de navigation utilisés dans l'UI.
 * Permet d'éviter de les répéter dans chaque écran.
 */
data class WildlensNavigationCallbacks(
    val onHomeClick: () -> Unit,
    val onLoginClick: () -> Unit,
    val onLogoutClick: () -> Unit,
    val onSettingsClick: () -> Unit,
    val onAnimalsClick: () -> Unit,
    val onProfileClick: () -> Unit,
    val onMyScansClick: () -> Unit,
    val onIAClick: () -> Unit
)
