package com.example.mspr_2025.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.mspr_2025.ui.navigation.Screen

@Composable
fun Login(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val navigationEvents = viewModel.navigationEvent

    LaunchedEffect(Unit) {
        navigationEvents.collect { event ->
            when (event) {
                LoginNavigationEvent.NavigateToHome -> navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
                LoginNavigationEvent.NavigateToRegister -> navController.navigate(Screen.Register.route)
                LoginNavigationEvent.NavigateBackToLogin -> navController.popBackStack()
            }
        }
    }
    LoginScreen(viewModel = viewModel)
}

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Mot de passe") })

        Button(onClick = {
            viewModel.login(email, password) {
                error = it
            }
        }) {
            Text("Se connecter")
        }

        TextButton(onClick = { viewModel.onNavigateToRegister() }) {
            Text("Pas encore de compte ? S'inscrire")
        }

        error?.let {
            Text(text = it, color = Color.Red)
        }
    }
}
