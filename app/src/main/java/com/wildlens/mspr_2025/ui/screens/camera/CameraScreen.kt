package com.wildlens.mspr_2025.ui.screens.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.wildlens.mspr_2025.ui.screens.camera.state.ScanViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    viewModel: ScanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(Manifest.permission.CAMERA)
    )

    val results by viewModel.results.collectAsState()
    val inferenceTime by viewModel.inferenceTime.collectAsState()

    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }

    when {
        permissionsState.allPermissionsGranted -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraPreviewComposable(
                    lifecycleOwner = lifecycleOwner,
                    context = context,
                    onResults = { classifications, timeMs ->
                        viewModel.onNewResults(classifications, timeMs)
                    }
                )

                val categories = results.firstOrNull()?.categories ?: emptyList()

                LazyColumn(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                ) {
                    item {
                        inferenceTime?.let {
                            Text(
                                "Inference time: $it ms",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    items(categories, key = { it.label }) { category ->
                        Text(
                            text = "${category.label}: ${(category.score * 100).toInt()}%",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        !permissionsState.shouldShowRationale && permissionsState.revokedPermissions.isNotEmpty() -> {
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

        else -> {
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
    }
}
