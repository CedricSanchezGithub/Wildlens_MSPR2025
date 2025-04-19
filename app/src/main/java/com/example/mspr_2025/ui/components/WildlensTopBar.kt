package com.example.mspr_2025.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mspr_2025.core.navigation.NavViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WildlensTopBar(
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLoginClick: () -> Unit,
    onClickTheme: () -> Unit,
) {

    val navViewModel: NavViewModel = hiltViewModel()
    val authManager = navViewModel.authManager
    val isLoggedIn by authManager.isLoggedIn.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Wildlens") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profil"
                        )
                    },
                    text = { Text("Profil") },
                    onClick = {
                        expanded = false
                        onProfileClick()
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Paramètres"
                        )
                    },
                    text = { Text("Paramètres") },
                    onClick = {
                        expanded = false
                        onSettingsClick()
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Brightness6, contentDescription = "Changer de thème")
                    },
                    text = { Text("Thème") },
                    onClick = {
                        onClickTheme()
                    }
                )

                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = if (isLoggedIn) "Déconnexion" else "Connexion"
                        )
                    },
                    text = {
                        Text(if (isLoggedIn) "Déconnexion" else "Connexion")
                    },
                    onClick = {
                        expanded = false
                        if (isLoggedIn) {
                            navViewModel.logout()
                        } else {
                            onLoginClick()
                        }
                    }
                )
            }
        }
    )
}

