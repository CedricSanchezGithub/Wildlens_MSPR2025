package com.wildlens.mspr_2025.presentation.screens.camera.ui

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.wildlens.mspr_2025.domain.camerax.imageclassification.CameraImageAnalyzer
import com.wildlens.mspr_2025.domain.camerax.imageclassification.ClassifierListener
import com.wildlens.mspr_2025.domain.camerax.imageclassification.ImageClassifierHelper
import com.wildlens.mspr_2025.presentation.screens.camera.state.ScanViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Composable qui affiche le flux de la caméra et exécute l'analyse en temps réel.
 *
 * Cette fonction met en place les cas d'usage CameraX suivants :
 * - Preview : affiche le flux vidéo en direct.
 * - ImageCapture : permet la capture de photos (via callback).
 * - ImageAnalysis : analyse chaque frame en temps réel pour classifier l'image via un modèle TFLite.
 *
 * Le modèle et le délégué sont configurables dynamiquement. L'analyse se fait sur un thread dédié
 * pour éviter de bloquer le thread principal.
 *
 * @param modifier Modificateur d'affichage.
 * @param modelIndex Index du modèle de classification sélectionné.
 * @param delegateIndex Type de délégué (CPU, GPU, NNAPI).
 * @param viewModel ViewModel pour les états de chargement/erreur/résultats.
 * @param onResults Callback déclenché avec les résultats de classification.
 * @param onImageCaptureReady Callback fournissant l'objet ImageCapture pour prendre des photos.
 */

@Composable
fun CameraPreviewComposable(
    modifier: Modifier = Modifier,
    modelIndex: Int,
    delegateIndex: Int,
    viewModel: ScanViewModel,
    onResults: (List<Classifications>?, Long) -> Unit,
    onImageCaptureReady: (ImageCapture) -> Unit
) {
    val currentContext = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val previewView = remember { PreviewView(currentContext) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(lifecycleOwner, modelIndex, delegateIndex, currentContext) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(currentContext)

        val cameraProviderListener = Runnable {
            try {
                setupCameraUseCases(
                    context = currentContext,
                    previewView = previewView,
                    modelIndex = modelIndex,
                    delegateIndex = delegateIndex,
                    viewModel = viewModel,
                    onResults = onResults,
                    onImageCaptureReady = onImageCaptureReady,
                    lifecycleOwner = lifecycleOwner,
                    cameraExecutor = cameraExecutor
                )
            } catch (e: Exception) {
                Log.e("CameraX", "Use case binding failed", e)
                viewModel.setError("Camera setup failed: ${e.localizedMessage}")
            }
        }

        cameraProviderFuture.addListener(cameraProviderListener, ContextCompat.getMainExecutor(currentContext))

        onDispose {
            cameraExecutor.shutdown()
            cameraProviderFuture.addListener({
                cameraProviderFuture.get()?.unbindAll()
            }, ContextCompat.getMainExecutor(currentContext))
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = modifier
    )
}

private fun setupCameraUseCases(
    context: android.content.Context,
    previewView: PreviewView,
    modelIndex: Int,
    delegateIndex: Int,
    viewModel: ScanViewModel,
    onResults: (List<Classifications>?, Long) -> Unit,
    onImageCaptureReady: (ImageCapture) -> Unit,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    cameraExecutor: ExecutorService
) {
    val cameraProvider = ProcessCameraProvider.getInstance(context).get()

    val preview = Preview.Builder().build().also {
        it.surfaceProvider = previewView.surfaceProvider
    }

    val imageCapture = ImageCapture.Builder().build()
    onImageCaptureReady(imageCapture)

    val imageClassifierHelper = ImageClassifierHelper(
        context = context,
        currentModel = modelIndex,
        currentDelegate = delegateIndex,
        viewModel = viewModel,
        imageClassifierListener = object : ClassifierListener {
            override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                onResults(results, inferenceTime)
            }

            override fun onError(error: String) {
                Log.e("CameraPreview", "Classifier Error: $error")
                viewModel.setError("Classifier Error: $error")
            }

            override fun onLoading() {
                viewModel.setLoading(true)
            }

            override fun onInitialized() {
                viewModel.setLoading(false)
            }
        }
    )

    val imageAnalysis = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also {
            it.setAnalyzer(cameraExecutor, CameraImageAnalyzer(imageClassifierHelper))
        }

    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    cameraProvider.unbindAll()
    cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        preview,
        imageAnalysis,
        imageCapture
    )
}
