package com.wildlens.mspr_2025.core.camerax.imageclassification

import org.tensorflow.lite.task.vision.classifier.Classifications

/**
 * Interface de callback pour recevoir les événements liés à la classification.
 *
 * - onError : déclenché en cas d’erreur lors de l’initialisation ou de l'inférence.
 * - onResults : renvoie les résultats de classification et le temps d’inférence.
 * - onLoading : appelé avant l’initialisation du modèle.
 * - onInitialized : appelé après l’initialisation du modèle.
 */

interface ClassifierListener {
    fun onError(error: String)
    fun onResults(results: List<Classifications>?, inferenceTime: Long)
    fun onLoading()
    fun onInitialized()
}
