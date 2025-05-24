package com.wildlens.mspr_2025.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wildlens.mspr_2025.presentation.screens.animals.ui.AnimalsScreen
import com.wildlens.mspr_2025.presentation.screens.auth.ui.AuthScreen
import com.wildlens.mspr_2025.presentation.screens.camera.ui.CameraScreen
import com.wildlens.mspr_2025.presentation.screens.home.ui.HomeScreen
import com.wildlens.mspr_2025.presentation.screens.iascreen.ui.IAScreen
import com.wildlens.mspr_2025.presentation.screens.scans.ui.MyScansScreen
import com.wildlens.mspr_2025.presentation.screens.profile.ui.ProfileScreen
import com.wildlens.mspr_2025.presentation.screens.accessibility.ui.AccessibilityScreen

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
        composable(AppRoute.Settings.route) { AccessibilityScreen(navController = navController) }
        composable(AppRoute.Animals.route) { AnimalsScreen(navController = navController) }
        composable(AppRoute.MyScans.route) { MyScansScreen(navController = navController) }
        composable(AppRoute.IA.route) { IAScreen(navController = navController) }
        composable(AppRoute.Profile.route) { ProfileScreen(navController = navController) }
        composable(AppRoute.Camera.route) { CameraScreen() }
    }
}

