package com.wildlens.mspr_2025.ui.screens.camera.presentation.components

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CaptureButton(
    context: Context,
    imageCapture: ImageCapture?,
    onPhotoCaptured: (Uri) -> Unit
){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(80.dp)
            .background(Color.Transparent, CircleShape)
            .clickable(enabled = imageCapture != null) {
                val capture = imageCapture ?: return@clickable

                val photoFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "photo_wildlens_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
                )
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                try {
                    capture.takePicture(
                        outputOptions,
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(photoFile)
                                Log.d("CameraCapture", "Photo saved at $savedUri")
                                onPhotoCaptured(savedUri)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Log.e("CameraCapture", "Capture failed: ${exception.message}", exception)
                            }
                        }
                    )
                } catch (e: Exception) {
                    Log.e("CameraCapture", "ImageCapture not ready: ${e.message}", e)
                }
            }
    ) {
        // Outer ring
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Transparent, CircleShape)
                .border(BorderStroke(4.dp, Color.White), CircleShape)
        )
        // Inner circle
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.White, CircleShape)
        )
    }


}