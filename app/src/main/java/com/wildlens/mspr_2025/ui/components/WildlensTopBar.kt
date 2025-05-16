package com.wildlens.mspr_2025.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.core.langage.AppLanguage
import com.wildlens.mspr_2025.core.langage.setAppLanguage
import com.wildlens.mspr_2025.core.navigation.AppRoute
import com.wildlens.mspr_2025.core.session.SessionViewModel
import com.wildlens.mspr_2025.core.theme.LocalToggleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WildlensTopBar(
    isLoggedIn: Boolean,
    navController: NavController
) {

    var expanded by remember { mutableStateOf(false) }
    val toggleTheme = LocalToggleTheme.current
    val sessionViewModel = hiltViewModel<SessionViewModel>()
    var showLanguageDialog by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconwhite),
                    contentDescription = "Logo",
                )
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Text("Wildlens")
            }
                },
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
                        navController.navigate(AppRoute.Profile.route)
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Accessibilité"
                        )
                    },
                    text = { Text("Paramètres") },
                    onClick = {
                        expanded = false
                        navController.navigate(AppRoute.Settings.route)
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Brightness6, contentDescription = "Changer de thème")
                    },
                    text = { Text("Thème") },
                    onClick = { toggleTheme() }
                )

                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.language))
                    },
                    text = { Text(stringResource(R.string.language)) },
                    onClick = {
                        expanded = false
                        showLanguageDialog = true
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
                            sessionViewModel.logout()
                        } else {
                            navController.navigate(AppRoute.Auth.route)
                        }
                    }
                )
            }
        }
    )
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(stringResource(android.R.string.cancel))
                }
            }
            ,
            title = { Text(stringResource(R.string.language)) },
            text = {
                Column {
                    AppLanguage.entries.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(stringResource(id = language.labelRes)) },
                            onClick = {
                                setAppLanguage(navController.context, language)
                                showLanguageDialog = false
                            }
                        )
                    }
                }
            }
        )
    }
}

