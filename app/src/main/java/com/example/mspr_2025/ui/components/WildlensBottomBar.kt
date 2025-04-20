package com.example.mspr_2025.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp



@Composable
fun WildlensBottomBar(
    onHomeClick: () -> Unit,
    onAnimalsClick: () -> Unit,
    onMyScansClick: () -> Unit,
) {
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
                onClick = { onHomeClick() }
            )
            BottomBarItem(
                icon = Icons.Default.Pets,
                label = "Animaux",
                onClick = { onAnimalsClick() }
            )
            BottomBarItem(
                icon = Icons.Default.Screenshot,
                label = "Mes scans",
                onClick = { onMyScansClick() }
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
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = label)
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
