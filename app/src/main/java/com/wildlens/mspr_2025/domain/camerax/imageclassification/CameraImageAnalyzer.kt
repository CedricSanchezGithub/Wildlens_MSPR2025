/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wildlens.mspr_2025.domain.camerax.imageclassification

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Analyse en temps réel les images de la caméra via ImageAnalysis.
 *
 * - Convertit chaque ImageProxy en Bitmap.
 * - Appelle la classification avec ImageClassifierHelper.
 * - Utilise un verrou (AtomicBoolean) pour éviter les traitements concurrents.
 *
 * S'utiliser avec CameraX via setAnalyzer().
 *
 * En résumé,
 * "CameraImageAnalyzer est l’élément qui reçoit les images de la caméra, les convertit,
 * et déclenche la classification IA.
 * Il agit comme une passerelle entre CameraX et TensorFlow Lite,
 * tout en évitant le traitement de plusieurs images simultanément."
 *
 */

class CameraImageAnalyzer(
    private val imageClassifierHelper: ImageClassifierHelper
) : ImageAnalysis.Analyzer {

    private val isProcessing = AtomicBoolean(false)

    override fun analyze(image: ImageProxy) {
        if (isProcessing.get()) {
            image.close()
            return
        }

        isProcessing.set(true)

        try {
            val bitmap = image.toBitmap()
            imageClassifierHelper.classify(bitmap, image.imageInfo.rotationDegrees)
        } catch (e: Exception) {
            Log.e("CameraAnalyzer", "Error processing image", e)
        } finally {
            isProcessing.set(false)
            image.close()
        }
    }
}