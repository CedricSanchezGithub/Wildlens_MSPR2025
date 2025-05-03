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

package com.wildlens.mspr_2025.core.camerax.imageclassification

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.util.concurrent.atomic.AtomicBoolean
import androidx.core.graphics.createBitmap

/**
 * Classe responsable d’analyser les images fournies par CameraX en temps réel
 * et de transmettre les frames au moteur de classification (TensorFlow Lite).
 *
 * Utilité :
 * - Convertit les `ImageProxy` en `Bitmap`
 * - Appelle `ImageClassifierHelper.classify()` avec les frames caméra
 * - Utilise un verrou (`isProcessing`) pour éviter de lancer plusieurs inférences simultanément
 *
 * 📌 Cette classe est conçue pour être passée à `ImageAnalysis.setAnalyzer()` dans CameraX.
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