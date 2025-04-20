package com.example.mspr_2025.ui.screens.home.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mspr_2025.core.navigation.NavViewModel
import com.example.mspr_2025.ui.screens.home.state.HomeViewModel

@Composable
fun HomeScreenSuccess(
    text: String,
){

    val navViewModel: NavViewModel = hiltViewModel()
    val viewModel: HomeViewModel = hiltViewModel()
    val authManager = navViewModel.authManager
    val isLoggedIn by authManager.isLoggedIn.collectAsStateWithLifecycle()
    val user by viewModel.user.collectAsStateWithLifecycle()

    if (isLoggedIn) {
        Text(text = text)
        user?.let {
            Text("Bonjour ${it.firstName} ${it.lastName} 👋")
        } ?: Text("Chargement des infos utilisateur...")
    } else {
        Text("Déconnecté")
    }
}

