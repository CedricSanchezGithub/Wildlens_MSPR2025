package com.example.mspr_2025.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mspr_2025.ui.screens.home.HomeScreen
import com.example.mspr_2025.ui.screens.login.Login
import com.example.mspr_2025.ui.screens.login.LoginNavigationEvent
import com.example.mspr_2025.ui.screens.login.LoginViewModel
import com.example.mspr_2025.ui.screens.login.Register

@Composable
fun WildlensNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Login.route) { backStackEntry ->
            val viewModel: LoginViewModel = hiltViewModel(backStackEntry)

            LaunchedEffect(Unit) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        LoginNavigationEvent.NavigateToHome -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                        LoginNavigationEvent.NavigateToRegister -> {
                            navController.navigate(Screen.Register.route)
                        }
                        else -> {}
                    }
                }
            }

            Login(viewModel = viewModel)
        }


        composable(Screen.Register.route) { backStackEntry ->
            val viewModel: LoginViewModel = hiltViewModel(backStackEntry)

            // Écoute les événements de navigation
            LaunchedEffect(Unit) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        LoginNavigationEvent.NavigateToHome -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                        LoginNavigationEvent.NavigateBackToLogin -> {
                            navController.popBackStack()
                        }
                        else -> {}
                    }
                }
            }

            Register(viewModel = viewModel)
        }

        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}
