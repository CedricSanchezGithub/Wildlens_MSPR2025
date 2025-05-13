package com.wildlens.mspr_2025.ui.screens.camera.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreenError(
    permissionsState : MultiplePermissionsState
) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Veuillez accorder l'autorisation de la caméra")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                permissionsState.launchMultiplePermissionRequest()
            }) {
                Text("Demander la permission")
            }
        }

}