package com.wildlens.mspr_2025.domain.camerax.imageclassification

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.view.Surface
import com.wildlens.mspr_2025.presentation.screens.camera.state.ScanViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

/**
 * Classe utilitaire pour la gestion du modèle TensorFlow Lite de classification d’images.
 *
 * - Gère l’initialisation du modèle en fonction de l’index sélectionné.
 * - Supporte plusieurs types de délégués (CPU, GPU, NNAPI).
 * - Télécharge un modèle distant si nécessaire (ici EfficientNetV4).
 * - Fournit un point d’entrée unique pour lancer des inférences sur des images via classify().
 *
 * À utiliser avec CameraX et ImageAnalysis.Analyzer pour de la classification en temps réel.
 */

class ImageClassifierHelper(
    var threshold: Float = 0.5f,
    var numThreads: Int = 2,
    var maxResults: Int = 3,
    var currentDelegate: Int = 0,
    var currentModel: Int = 0,
    val context: Context,
    val viewModel: ScanViewModel,
    val imageClassifierListener: ClassifierListener?
) {
    private var imageClassifier: ImageClassifier? = null
    private var isInitializing = false

    init {
        CoroutineScope(Dispatchers.Main).launch {
            setupImageClassifier()
        }
    }

    fun clearImageClassifier() {
        imageClassifier = null
    }

    suspend fun setupImageClassifier() {
        if (isInitializing) return
        isInitializing = true
        viewModel.setLoading(true)
        imageClassifierListener?.onLoading()

        val options = buildImageClassifierOptions(
            threshold,
            maxResults,
            numThreads,
            currentDelegate,
            imageClassifierListener?.let { listener -> { error -> listener.onError(error) } } ?: {}
        )
        val modelFile = getModelFileIfNeeded(context, currentModel)

        try {
            imageClassifier = when {
                modelFile?.exists() == true -> {
                    ImageClassifier.createFromFileAndOptions(modelFile, options)
                }

                currentModel == MODEL_EFFICIENTNETV4 -> {
                    imageClassifierListener?.onError("Le modèle EfficientNetV4 n’a pas été trouvé ni téléchargé.")
                    return
                }

                else -> {
                    val modelName = when (currentModel) {
                        MODEL_MOBILENETV1 -> "mobilenetv1.tflite"
                        MODEL_EFFICIENTNETV0 -> "efficientnet-lite0.tflite"
                        MODEL_EFFICIENTNETV1 -> "efficientnet-lite1.tflite"
                        MODEL_EFFICIENTNETV2 -> "efficientnet-lite2.tflite"
                        else -> "mobilenetv1.tflite"
                    }
                    ImageClassifier.createFromFileAndOptions(context, modelName, options)
                }
            }
            imageClassifierListener?.onInitialized()
        } catch (e: IllegalStateException) {
            imageClassifierListener?.onError("Image classifier failed: ${e.message}")
        } finally {
            isInitializing = false
            viewModel.setLoading(false)
        }
    }

    fun classify(image: Bitmap, rotation: Int) {
        if (imageClassifier == null) {
            CoroutineScope(Dispatchers.Main).launch {
                setupImageClassifier()
            }
            return
        }

        var inferenceTime = SystemClock.uptimeMillis()

        val imageProcessor = ImageProcessor.Builder().build()
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(image))

        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(rotation))
            .build()

        val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        imageClassifierListener?.onResults(results, inferenceTime)
    }

    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1
        const val DELEGATE_NNAPI = 2

        const val MODEL_MOBILENETV1 = 0
        const val MODEL_EFFICIENTNETV0 = 1
        const val MODEL_EFFICIENTNETV1 = 2
        const val MODEL_EFFICIENTNETV2 = 3
        const val MODEL_EFFICIENTNETV4 = 4
    }
}