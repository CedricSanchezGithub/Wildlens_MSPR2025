package com.example.mspr_2025.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mspr_2025.ui.screens.auth.presentation.AuthScreen
import com.example.mspr_2025.ui.screens.home.presentation.HomeScreen
import androidx.compose.runtime.getValue
import com.example.mspr_2025.ui.screens.animals.presentation.AnimalsScreen

@Composable
fun MainGraph(
    router: AppRouter = hiltViewModel<NavViewModel>().router
) {
    val route by router.route.collectAsStateWithLifecycle()

    val navigation = WildlensNavigationCallbacks(
        onHomeClick = { router.navigate(AppRoute.Home) },
        onLoginClick = { router.navigate(AppRoute.Auth) },
        onLogoutClick = { /* si besoin */ },
        onSettingsClick = { router.navigate(AppRoute.Settings) },
        onAnimalsClick = { router.navigate(AppRoute.Animals) },
        onProfileClick = { router.navigate(AppRoute.Profile) }
    )

    when (route) {
        is AppRoute.Auth -> AuthScreen(navigationCallbacks = navigation)

        is AppRoute.Home -> HomeScreen(navigationCallbacks = navigation)

        is AppRoute.Animals -> AnimalsScreen(navigationCallbacks = navigation)

        is AppRoute.Favorites -> TODO()
        is AppRoute.Profile -> TODO()
        is AppRoute.Settings -> TODO()
    }
}
