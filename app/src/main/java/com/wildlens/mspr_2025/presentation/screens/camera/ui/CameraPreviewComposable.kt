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
import java.util.concurrent.Executors

/**
 * Composable that displays the camera preview using CameraX and PreviewView.
 *
 * This function sets up CameraX use cases:
 *  - Preview: To display the camera stream.
 *  - ImageCapture: To take photos (exposed via onImageCaptureReady callback).
 *  - ImageAnalysis: To perform real-time image classification with TensorFlow Lite.
 *
 * @param modifier Modifier for this composable.
 * @param modelIndex Index of the selected classification model.
 * @param delegateIndex Index of the inference delegate (CPU/GPU/NNAPI).
 * @param viewModel ViewModel used to track loading state and errors.
 * @param onResults Callback invoked with classification results and inference time.
 * @param onImageCaptureReady Callback exposing the ImageCapture instance for photo capture.
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
    // var imageCaptureInstance by remember { mutableStateOf<ImageCapture?>(null) } // No longer needed if passed directly

    // Remember a dedicated executor for image analysis
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(lifecycleOwner, modelIndex, delegateIndex, currentContext) {
        lateinit var imageCapture: ImageCapture // Local to DisposableEffect
        val cameraProviderFuture = ProcessCameraProvider.getInstance(currentContext)

        val cameraProviderListener = Runnable {
            try {
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                imageCapture = ImageCapture.Builder().build()
                // imageCaptureInstance = imageCapture // Not strictly needed if only using callback
                onImageCaptureReady(imageCapture)

                val imageClassifierHelper = ImageClassifierHelper(
                    context = currentContext,
                    currentModel = modelIndex,
                    currentDelegate = delegateIndex,
                    viewModel = viewModel, // viewModel can be passed directly
                    imageClassifierListener = object : ClassifierListener {
                        override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                            onResults(results, inferenceTime)
                        }

                        override fun onError(error: String) {
                            Log.e("CameraPreview", "Classifier Error: $error")
                            viewModel.setError("Classifier Error: $error") // Update ViewModel
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
                        it.setAnalyzer(
                            cameraExecutor, // Use background executor for analysis
                            CameraImageAnalyzer(imageClassifierHelper)
                        )
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                cameraProvider.unbindAll() // Unbind previous use cases before binding new ones
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e("CameraX", "Use case binding failed", e)
                viewModel.setError("Camera setup failed: ${e.localizedMessage}")
            }
        }

        cameraProviderFuture.addListener(cameraProviderListener, ContextCompat.getMainExecutor(currentContext))

        onDispose {
            // Shut down the executor
            cameraExecutor.shutdown()
            // Unbind camera use cases
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