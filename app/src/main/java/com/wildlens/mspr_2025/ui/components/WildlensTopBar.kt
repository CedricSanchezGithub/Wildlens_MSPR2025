package com.wildlens.mspr_2025.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import com.wildlens.mspr_2025.core.navigation.AppRoute
import com.wildlens.mspr_2025.core.session.SessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WildlensTopBar(
    isLoggedIn: Boolean,
    navController: NavController
) {

    var expanded by remember { mutableStateOf(false) }
    val sessionViewModel = hiltViewModel<SessionViewModel>()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor =  MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        title = {
            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconwhite),
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Text(stringResource(id = R.string.app_name))
            }
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = stringResource(id = R.string.menu))
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = stringResource(id = R.string.menu_profile))
                    },
                    text = { Text(stringResource(id = R.string.menu_profile)) },
                    onClick = {
                        expanded = false
                        navController.navigate(AppRoute.Profile.route)
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(id = R.string.menu_accessibility))
                    },
                    text = { Text(stringResource(id = R.string.menu_settings)) },
                    onClick = {
                        expanded = false
                        navController.navigate(AppRoute.Settings.route)
                    }
                )
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = stringResource(
                                id = if (isLoggedIn) R.string.menu_logout else R.string.menu_login
                            )
                        )
                    },
                    text = {
                        Text(stringResource(id = if (isLoggedIn) R.string.menu_logout else R.string.menu_login))
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
}

