package com.wildlens.mspr_2025.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Router centralisé basé sur un StateFlow pour une navigation déclarative.
 * @param initialRoute La route sur laquelle l'application démarre.
 */

class AppRouter(initialRoute: AppRoute) {
    private val _route = MutableStateFlow(initialRoute)
    val route: StateFlow<AppRoute> = _route

    fun navigate(to: AppRoute) {
        _route.value = to
    }
}
