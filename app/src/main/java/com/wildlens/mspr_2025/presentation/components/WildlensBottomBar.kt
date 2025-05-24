package com.wildlens.mspr_2025.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.domain.navigation.AppRoute


@Composable
fun WildlensBottomBar(navController : NavController) {

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomBarItem(
                icon = Icons.Default.Home,
                label = stringResource(id = R.string.home),
                onClick = { navController.navigate(AppRoute.Home.route) }
            )
            BottomBarItem(
                icon = Icons.Default.Pets,
                label = stringResource(R.string.animals),
                onClick = { navController.navigate(AppRoute.Animals.route) },

            )
            BottomBarItem(
                icon = Icons.Default.Screenshot,
                label = stringResource(R.string.my_scans),
                onClick = { navController.navigate(AppRoute.MyScans.route) }
            )
            BottomBarItem(
                icon = Icons.Default.AddChart,
                label = stringResource(R.string.ia),
                onClick = { navController.navigate(AppRoute.IA.route) }
            )
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
        IconButton(onClick = onClick){
            Icon(icon, contentDescription = label)
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
