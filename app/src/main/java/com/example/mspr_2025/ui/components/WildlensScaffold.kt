package com.example.mspr_2025.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import com.example.mspr_2025.ui.theme.LocalToggleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WildlensScaffold(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit = {},
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit = {},
    onLoginClick: () -> Unit,
    onAnimalsClick: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (PaddingValues) -> Unit
) {
    val toggleTheme = LocalToggleTheme.current

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
                onSettingsClick = onSettingsClick,
                onProfileClick = onProfileClick,
                onLoginClick = onLoginClick,
                onClickTheme = { toggleTheme() },
            )
        },
        bottomBar = {
            WildlensBottomBar(
                onHomeClick = onHomeClick,
                onAnimalsClick = onAnimalsClick,
                onHelpClick = onHelpClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content(paddingValues)
        }
    }
}

