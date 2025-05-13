package com.wildlens.mspr_2025.ui.screens.camera.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CapturedThumbnail(uri: Uri) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary)
            .size(100.dp)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = uri),
            contentDescription = "Captured photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
