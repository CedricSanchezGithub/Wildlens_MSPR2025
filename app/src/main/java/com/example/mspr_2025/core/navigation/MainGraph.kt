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

    when (route) {
        is AppRoute.Auth -> AuthScreen(
            onHomeClick = { router.navigate(AppRoute.Home) }
            )

        is AppRoute.Home -> HomeScreen(
            onSettingsClick = { router.navigate(AppRoute.Settings) },
            onLoginClick = { router.navigate(AppRoute.Auth) },
            onHomeClick = { router.navigate(AppRoute.Home) },
            onAnimalsClick = { router.navigate(AppRoute.Animals) }
        )

        is AppRoute.Animals -> AnimalsScreen(
            onSettingsClick = { router.navigate(AppRoute.Settings) },
            onLoginClick = { router.navigate(AppRoute.Auth) },
            onHomeClick = { router.navigate(AppRoute.Home) },
        )

        is AppRoute.Favorites -> TODO()
        is AppRoute.Profile -> TODO()
        is AppRoute.Settings -> TODO()
    }
}