package com.wildlens.mspr_2025.presentation.screens.camera.ui.components

import DropdownMenuWithIndex
import android.content.Context
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.presentation.screens.camera.state.ScanViewModel
import java.io.File

@Composable
fun BottomControlsPanel(
    context: Context,
    imageCapture: ImageCapture?,
    viewModel: ScanViewModel,
    modelIndex: Int,
    delegateIndex: Int,
    isGpuSupported: Boolean,
    isNnapiSupported: Boolean,
    onModelSelected: (Int) -> Unit,
    onDelegateSelected: (Int) -> Unit,
) {

    val isLoading by viewModel.isLoading.collectAsState()

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                DropdownMenuWithIndex(
                    items = listOf("MobileNetV1", "EfficientNetV0", "EfficientNetV1", "EfficientNetV2", "EfficientNetV4"),
                    selectedIndex = modelIndex,
                    onSelected = onModelSelected
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                CaptureButton(
                    context = context,
                    imageCapture = imageCapture,
                    onPhotoCaptured = { uri ->
                        val file = File(uri.path ?: return@CaptureButton)
                        viewModel.uploadCapturedImage(file) { success ->
                            val msg = if (success) "Image envoyée avec succès" else "Échec de l'envoi"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                DropdownMenuWithIndex(
                    items = listOf("CPU", "GPU", "NNAPI"),
                    selectedIndex = delegateIndex,
                    onSelected = onDelegateSelected,
                    enabledStates = listOf(true, isGpuSupported, isNnapiSupported)
                )
            }
        }

        // Overlay de chargement
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

