package com.wildlens.mspr_2025.ui.screens.camera

import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.wildlens.mspr_2025.core.camerax.imageclassification.CameraImageAnalyzer
import com.wildlens.mspr_2025.core.camerax.imageclassification.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications

@Composable
fun CameraPreviewComposable(
    lifecycleOwner: LifecycleOwner,
    context: android.content.Context,
    modifier: Modifier = Modifier,
    onResults: (List<Classifications>?, Long) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().apply {
                    surfaceProvider = previewView.surfaceProvider
                }

                val imageClassifierHelper = ImageClassifierHelper(
                    context = ctx,
                    imageClassifierListener = object : ImageClassifierHelper.ClassifierListener {
                        override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                            onResults(results, inferenceTime)
                        }

                        override fun onError(error: String) {
                            // log optionnel
                        }
                    }
                )

                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .apply {
                        setAnalyzer(
                            ContextCompat.getMainExecutor(ctx),
                            CameraImageAnalyzer(imageClassifierHelper)
                        )
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.e("CameraX", "Échec du bind des use cases", e)
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}
