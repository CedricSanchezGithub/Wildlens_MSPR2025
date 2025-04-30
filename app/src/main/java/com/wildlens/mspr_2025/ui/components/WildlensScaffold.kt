package com.wildlens.mspr_2025.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WildlensScaffold(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onClickTheme: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    isLoggedIn: Boolean = false,
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Action photo */ },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(Icons.Rounded.AddAPhoto, contentDescription = "Ajouter une photo")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            WildlensTopBar(
                onClickTheme = onClickTheme,
                isLoggedIn = isLoggedIn,
                onLogoutClick = onLogoutClick,
                navController = navController
            )
        },
        bottomBar = {
            WildlensBottomBar(navController = navController)
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
