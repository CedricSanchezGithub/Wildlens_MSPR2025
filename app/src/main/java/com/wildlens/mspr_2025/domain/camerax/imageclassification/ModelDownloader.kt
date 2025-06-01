package com.wildlens.mspr_2025.domain.camerax.imageclassification

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
* Gère le téléchargement des modèles TFLite à la demande.
*
* Permet de récupérer un modèle distant (ex : EfficientNetV4) et de le stocker localement
* pour une utilisation hors-ligne avec ImageClassifier.
*/

object ModelDownloader {

    private val modelUrls = mapOf(
        4 to "https://github.com/tensorflow/examples/raw/master/lite/examples/image_classification/android/app/src/main/assets/efficientnet-lite4.tflite",
        5 to "notre prochain modèle à télécharger"
    )
    /**
    * Télécharge le modèle correspondant à l’index si non déjà présent localement.
    *
    * @param context Le contexte utilisé pour le répertoire de stockage.
    * @param modelIndex L’index du modèle à télécharger.
    * @return Le fichier du modèle local ou null en cas d’échec.
    */

    suspend fun getModelFile(context: Context, modelIndex: Int): File? {
        val modelUrl = modelUrls[modelIndex] ?: return null
        val fileName = modelUrl.substringAfterLast("/")
        val file = File(context.filesDir, fileName)

        if (file.exists()) return file

        return try {
            withContext(Dispatchers.IO) {
                val url = URL(modelUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("ModelDownloader", "Server returned HTTP ${connection.responseCode}")
                    return@withContext null
                }

                val inputStream = connection.inputStream
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()

                file
            }
        } catch (e: Exception) {
            Log.e("ModelDownloader", "Download failed", e)
            null
        }
    }
}
