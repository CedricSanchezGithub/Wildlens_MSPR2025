package com.wildlens.mspr_2025.core.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

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
    object MyScans: AppRoute
}

@OptIn(ExperimentalAnimationApi::class)
fun AppRoute.transitionSpec(): ContentTransform {
    return when (this) {
        is AppRoute.Settings, is AppRoute.Profile ->
            fadeIn(tween(300)) + scaleIn(initialScale = 0.95f) togetherWith
                    fadeOut(tween(200)) + scaleOut(targetScale = 1.05f)

        is AppRoute.Auth ->
            fadeIn(tween(250)) togetherWith fadeOut(tween(200))

        else ->
            fadeIn(tween(250)) togetherWith fadeOut(tween(200))
    }
}
