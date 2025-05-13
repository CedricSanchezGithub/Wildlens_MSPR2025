package com.wildlens.mspr_2025.ui.screens.camera.presentation.components

import DropdownMenuWithIndex
import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomControlsPanel(
    context: Context,
    imageCapture: ImageCapture?,
    onPhotoCaptured: (Uri) -> Unit,
    modelIndex: Int,
    delegateIndex: Int,
    isGpuSupported: Boolean,
    isNnapiSupported: Boolean,
    onModelSelected: (Int) -> Unit,
    onDelegateSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dropdown 1 (Modèle)
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            DropdownMenuWithIndex(
                items = listOf("MobileNetV1", "EfficientNetV0", "EfficientNetV1", "EfficientNetV2", "EfficientNetV4"),
                selectedIndex = modelIndex,
                onSelected = onModelSelected
            )
        }

        // Bouton de capture
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CaptureButton(
                context = context,
                imageCapture = imageCapture,
                onPhotoCaptured = onPhotoCaptured
            )
        }

        // Dropdown 2 (Délégué)
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            DropdownMenuWithIndex(
                items = listOf("CPU", "GPU", "NNAPI"),
                selectedIndex = delegateIndex,
                onSelected = onDelegateSelected,
                enabledStates = listOf(true, isGpuSupported, isNnapiSupported)
            )
        }
    }

}
