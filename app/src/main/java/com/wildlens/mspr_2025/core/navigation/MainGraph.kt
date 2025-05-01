package com.wildlens.mspr_2025.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wildlens.mspr_2025.ui.screens.animals.presentation.AnimalsScreen
import com.wildlens.mspr_2025.ui.screens.auth.presentation.AuthScreen
import com.wildlens.mspr_2025.ui.screens.home.presentation.HomeScreen
import com.wildlens.mspr_2025.ui.screens.iascreen.presentation.IAScreen
import com.wildlens.mspr_2025.ui.screens.myscans.presentation.MyScansScreen
import com.wildlens.mspr_2025.ui.screens.profile.presentation.ProfileScreen
import com.wildlens.mspr_2025.ui.screens.settings.presentation.SettingsScreen

@Composable
fun MainGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppRoute.Auth.route) { AuthScreen(navController = navController) }
        composable(AppRoute.Home.route) { HomeScreen(navController = navController) }
        composable(AppRoute.Settings.route) { SettingsScreen(navController = navController) }
        composable(AppRoute.Animals.route) { AnimalsScreen(navController = navController) }
        composable(AppRoute.MyScans.route) { MyScansScreen(navController = navController) }
        composable(AppRoute.IA.route) { IAScreen(navController = navController) }
        composable(AppRoute.Profile.route) { ProfileScreen(navController = navController) }
    }
}

