package com.wildlens.mspr_2025.ui.screens.camera.state


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.SimpleDateFormat
import java.util.*

data class ClassificationResult(
    val timestamp: String,
    val results: List<Classifications>,
    val inferenceTime: Long
)

class ScanViewModel : ViewModel() {

    private val _results = MutableStateFlow<List<Classifications>>(emptyList())
    val results = _results.asStateFlow()

    private val _inferenceTime = MutableStateFlow<Long?>(null)
    val inferenceTime = _inferenceTime.asStateFlow()

    private val _resultsHistory = MutableStateFlow<List<ClassificationResult>>(emptyList())
    val resultsHistory = _resultsHistory.asStateFlow()

    private val _modelIndex = MutableStateFlow(0)
    val modelIndex = _modelIndex.asStateFlow()

    private val _delegateIndex = MutableStateFlow(0)
    val delegateIndex = _delegateIndex.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val dateFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    // Limiter l'historique à un certain nombre d'éléments
    private val MAX_HISTORY_ITEMS = 10

    fun onNewResults(results: List<Classifications>?, timeMs: Long) {
        if (results != null) {
            _results.value = results
            _inferenceTime.value = timeMs

            // Ne sauvegarder que les résultats avec au moins une catégorie et un score significatif
            if (results.isNotEmpty() && results.first().categories.isNotEmpty() &&
                results.first().categories.first().score > 0.3f) {

                val timestamp = dateFormatter.format(Date())
                val newResult = ClassificationResult(timestamp, results, timeMs)

                // Ajouter au début de la liste et limiter la taille
                _resultsHistory.value = (_resultsHistory.value + newResult)
                    .take(MAX_HISTORY_ITEMS)
            }
        }
    }

    fun updateModel(index: Int) {
        _modelIndex.value = index
    }

    fun updateDelegate(index: Int) {
        _delegateIndex.value = index
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun clearHistory() {
        _resultsHistory.value = emptyList()
    }
}