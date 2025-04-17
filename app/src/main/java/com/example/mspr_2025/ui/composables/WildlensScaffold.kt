package com.example.mspr_2025.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.mspr_2025.ui.theme.LocalToggleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WildlensScaffold(
    content: @Composable () -> Unit
) {
    Scaffold(
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
                onSettingsClick = { /* Action Paramètres */ },
                onProfileClick = { /* Action Profil */ },
                onLogoutClick = { /* Action Déconnexion */ },
                onToggleTheme = { /* Action Changer de thème */ }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomBarItem(
                        icon = Icons.Default.Home,
                        label = "Accueil",
                        onClick = { /* Action Accueil */ }
                    )
                    BottomBarItem(
                        icon = Icons.Default.Favorite,
                        label = "Favoris",
                        onClick = { /* Action Favoris */ }
                    )
                    BottomBarItem(
                        icon = Icons.AutoMirrored.Filled.Help,
                        label = "Aide",
                        onClick = { /* Action Aide */ }
                    )
                    BottomBarItem(
                        icon = Icons.Default.Settings,
                        label = "Paramètres",
                        onClick = { /* Action Paramètres */ }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = label)
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WildlensTopBar(
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onToggleTheme: () -> Unit

) {
    var expanded by remember { mutableStateOf(false) }
    val toggleTheme = LocalToggleTheme.current
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
                        toggleTheme()
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Déconnexion"
                        )
                    },
                    text = { Text("Déconnexion") },
                    onClick = {
                        expanded = false
                        onLogoutClick()
                    }
                )
            }
        }
    )
}


