package com.example.mspr_2025.core.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mspr_2025.ui.screens.auth.presentation.AuthScreen
import com.example.mspr_2025.ui.screens.home.presentation.HomeScreen
import androidx.compose.runtime.getValue
import com.example.mspr_2025.ui.screens.animals.presentation.AnimalsScreen
import com.example.mspr_2025.ui.screens.myscans.presentation.MyScansScreen
import com.example.mspr_2025.ui.screens.settings.presentation.SettingsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainGraph(
    router: AppRouter = hiltViewModel<NavViewModel>().router
) {
    val route by router.route.collectAsStateWithLifecycle()

    val navigation = WildlensNavigationCallbacks(
        onHomeClick = { router.navigate(AppRoute.Home) },
        onLoginClick = { router.navigate(AppRoute.Auth) },
        onSettingsClick = { router.navigate(AppRoute.Settings) },
        onAnimalsClick = { router.navigate(AppRoute.Animals) },
        onProfileClick = { router.navigate(AppRoute.Profile) },
        onMyScansClick = { router.navigate(AppRoute.MyScans) },
        onLogoutClick = { router.navigate(AppRoute.Home) },

    )

    AnimatedContent(
        targetState = route,
        transitionSpec = {
            targetState.transitionSpec()
        },
        label = "RouteTransition"
    ) { currentRoute ->
        when (currentRoute) {
            is AppRoute.Auth -> AuthScreen(navigationCallbacks = navigation)
            is AppRoute.Home -> HomeScreen(navigationCallbacks = navigation)
            is AppRoute.Animals -> AnimalsScreen(navigationCallbacks = navigation)
            is AppRoute.MyScans -> MyScansScreen(navigationCallbacks = navigation)
            is AppRoute.Settings -> SettingsScreen(navigationCallbacks = navigation)
            is AppRoute.Favorites -> TODO()
            is AppRoute.Profile -> TODO()
        }
    }
}
