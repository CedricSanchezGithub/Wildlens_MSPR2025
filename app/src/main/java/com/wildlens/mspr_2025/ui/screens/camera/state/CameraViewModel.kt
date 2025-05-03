package com.wildlens.mspr_2025.ui.screens.camera.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tensorflow.lite.task.vision.classifier.Classifications

class ScanViewModel : ViewModel() {

    private val _results = MutableStateFlow<List<Classifications>>(emptyList())
    val results = _results.asStateFlow()

    private val _inferenceTime = MutableStateFlow<Long?>(null)
    val inferenceTime = _inferenceTime.asStateFlow()

    fun onNewResults(results: List<Classifications>?, timeMs: Long) {
        if (results != null) {
            _results.value = results
            _inferenceTime.value = timeMs
        }
    }
}
