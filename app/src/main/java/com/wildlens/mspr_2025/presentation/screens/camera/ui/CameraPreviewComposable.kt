package com.wildlens.mspr_2025.presentation.screens.camera.ui

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.wildlens.mspr_2025.domain.camerax.imageclassification.CameraImageAnalyzer
import com.wildlens.mspr_2025.domain.camerax.imageclassification.ImageClassifierHelper
import com.wildlens.mspr_2025.domain.camerax.imageclassification.ClassifierListener
import com.wildlens.mspr_2025.presentation.screens.camera.state.ScanViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications

/**
 * Composable affichant l’aperçu de la caméra via CameraX et PreviewView.
 *
 * Cette fonction configure les use cases de CameraX :
 *  Preview : pour afficher le flux caméra
 *  ImageCapture : pour prendre des photos (exposé via le callback onImageCaptureReady)
 *  ImageAnalysis : pour effectuer une classification d’images en temps réel avec TensorFlow Lite
 *
 * @param lifecycleOwner L’observateur de cycle de vie utilisé pour lier les use cases
 * @param modifier Modificateur d'affichage du composant
 * @param context Contexte Android requis pour CameraX et TFLite
 * @param modelIndex Index du modèle de classification sélectionné
 * @param delegateIndex Index du délégué d’inférence (CPU/GPU/NNAPI)
 * @param viewModel ViewModel utilisé pour suivre l’état de chargement
 * @param onResults Callback appelé à chaque résultat de classification avec le temps d’inférence
 * @param onImageCaptureReady Callback exposant l’instance d’ImageCapture pour la capture de photo
 */

// Référence globale utilisée pour capturer des photos
lateinit var imageCapture: ImageCapture

@Composable
fun CameraPreviewComposable(
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier,
    context: Context,
    modelIndex: Int,
    delegateIndex: Int,
    viewModel: ScanViewModel,
    onResults: (List<Classifications>?, Long) -> Unit,
    onImageCaptureReady: (ImageCapture) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView = PreviewView(ctx) // Vue utilisée pour afficher le flux caméra
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx) // Fournisseur CameraX

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().apply {
                    surfaceProvider = previewView.surfaceProvider // Lien entre le flux caméra et l’affichage
                }

                imageCapture = ImageCapture.Builder().build() // Use case pour la capture d'image
                onImageCaptureReady(imageCapture)

                val imageClassifierHelper = ImageClassifierHelper(
                    context = ctx,
                    currentModel = modelIndex,
                    currentDelegate = delegateIndex,
                    viewModel = viewModel,
                    imageClassifierListener = object : ClassifierListener {
                        override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                            onResults(results, inferenceTime) // Callback de résultat depuis la classification
                        }

                        override fun onError(error: String) {
                            Log.e("CameraPreview", error) // Gestion des erreurs
                        }

                        override fun onLoading() {
                            viewModel.setLoading(true) // Début de l’initialisation du modèle
                        }

                        override fun onInitialized() {
                            viewModel.setLoading(false) // Fin de l’initialisation du modèle
                        }
                    }
                )

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .apply {
                        // Use case d'analyse d’image avec notre classifieur personnalisé
                        setAnalyzer(
                            ContextCompat.getMainExecutor(ctx),
                            CameraImageAnalyzer(imageClassifierHelper)
                        )
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // Sélection de la caméra arrière

                try {
                    cameraProvider.unbindAll() // Nettoyer les anciens use cases
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis,
                        imageCapture
                    ) // Reconfiguration complète de CameraX
                } catch (e: Exception) {
                    Log.e("CameraX", "Échec du bind des use cases", e)
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView // Retourne la vue de prévisualisation pour l’affichage à l’écran
        }
    )
}
