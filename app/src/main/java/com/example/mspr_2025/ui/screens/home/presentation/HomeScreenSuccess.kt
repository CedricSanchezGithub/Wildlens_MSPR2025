package com.example.mspr_2025.ui.screens.home.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mspr_2025.core.navigation.NavViewModel

@Composable
fun HomeScreenSuccess(text: String){

        val navViewModel: NavViewModel = hiltViewModel()
        val authManager = navViewModel.authManager
        val isLoggedIn by authManager.isLoggedIn.collectAsStateWithLifecycle()

        Text(text = text)
        if(isLoggedIn) {
            Text("Connecté")
        } else {
            Text("Déconnecté")
        }
    }

