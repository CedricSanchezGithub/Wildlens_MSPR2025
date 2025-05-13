package com.wildlens.mspr_2025.ui.screens.camera.presentation.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PermissionPrompt(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("La permission caméra a été refusée définitivement.\nVeuillez l'activer dans les paramètres de l'application.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }) {
            Text("Ouvrir les paramètres")
        }
    }
}
