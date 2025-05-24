package com.wildlens.mspr_2025.domain.camerax.imageclassification


import android.content.Context
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

/**
 * Télécharge le modèle EfficientNetV4 si nécessaire.
 *
 * @param context Le contexte Android utilisé pour l’accès au cache.
 * @param modelIndex L’index du modèle à vérifier.
 * @return Un fichier .tflite si EfficientNetV4 est demandé, sinon null.
 */

suspend fun getModelFileIfNeeded(context: Context, modelIndex: Int): java.io.File? {
    return if (modelIndex == ImageClassifierHelper.MODEL_EFFICIENTNETV4) {
        ModelDownloader.getModelFile(context, modelIndex)
    } else null
}


/**
 * Construit les options de configuration pour le classifieur d’image.
 *
 * @param threshold Le seuil de score minimal à retenir.
 * @param maxResults Le nombre maximal de résultats à retourner.
 * @param numThreads Le nombre de threads à utiliser.
 * @param delegate Le type de délégué à utiliser (CPU, GPU, NNAPI).
 * @param onError Callback appelé si une erreur est détectée (ex : GPU non supporté).
 * @return Une instance configurée de ImageClassifierOptions.
 */

fun buildImageClassifierOptions(
    threshold: Float,
    maxResults: Int,
    numThreads: Int,
    delegate: Int,
    onError: (String) -> Unit = {}
): ImageClassifier.ImageClassifierOptions {
    val baseOptionsBuilder = BaseOptions.builder().setNumThreads(numThreads)
    when (delegate) {
        ImageClassifierHelper.DELEGATE_GPU -> {
            if (CompatibilityList().isDelegateSupportedOnThisDevice) {
                baseOptionsBuilder.useGpu()
            } else {
                onError("GPU non supporté")
            }
        }
        ImageClassifierHelper.DELEGATE_NNAPI -> baseOptionsBuilder.useNnapi()
    }
    return ImageClassifier.ImageClassifierOptions.builder()
        .setScoreThreshold(threshold)
        .setMaxResults(maxResults)
        .setBaseOptions(baseOptionsBuilder.build())
        .build()
}
