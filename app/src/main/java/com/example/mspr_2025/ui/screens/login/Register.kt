package com.example.mspr_2025.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Register(
    viewModel: LoginViewModel = viewModel()
) {
    RegisterScreen(viewModel = viewModel)
}

@Composable
fun RegisterScreen(
    viewModel: LoginViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") }
        )

        Button(onClick = {
            viewModel.register(email, password) {
                error = it
            }
        }) {
            Text("Créer un compte")
        }

        TextButton(onClick = { viewModel.onNavigateBackToLogin() }) {
            Text("Déjà un compte ? Se connecter")
        }

        error?.let {
            Text(text = it, color = Color.Red)
        }
    }
}
