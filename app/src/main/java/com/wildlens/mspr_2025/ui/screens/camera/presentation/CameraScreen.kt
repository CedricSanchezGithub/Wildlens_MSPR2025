package com.wildlens.mspr_2025.ui.screens.camera.presentation

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.wildlens.mspr_2025.ui.screens.camera.presentation.components.*
import com.wildlens.mspr_2025.ui.screens.camera.state.ScanViewModel
import org.tensorflow.lite.gpu.CompatibilityList

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    viewModel: ScanViewModel = hiltViewModel(),
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(Manifest.permission.CAMERA)
    )

    val results by viewModel.results.collectAsState()
    val inferenceTime by viewModel.inferenceTime.collectAsState()
    val modelIndex by viewModel.modelIndex.collectAsState()
    val delegateIndex by viewModel.delegateIndex.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val isGpuSupported = remember { CompatibilityList().isDelegateSupportedOnThisDevice }
    val isNnapiSupported = remember { Build.VERSION.SDK_INT >= Build.VERSION_CODES.P }

    var capturedPhotoUri by remember { mutableStateOf<Uri?>(null) }
    val imageCaptureRef = remember { mutableStateOf<ImageCapture?>(null) }

    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }

    when {
        permissionsState.allPermissionsGranted -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraPreviewComposable(
                    lifecycleOwner = lifecycleOwner,
                    context = context,
                    onResults = { classifications, timeMs ->
                        viewModel.onNewResults(classifications, timeMs)
                    },
                    modelIndex = modelIndex,
                    delegateIndex = delegateIndex,
                    viewModel = viewModel,
                    onImageCaptureReady = { imageCaptureRef.value = it }
                )

                if (isLoading) {
                    CameraLoadingOverlay()
                }

                capturedPhotoUri?.let { CapturedThumbnail(it) }

                val resultsHistory by viewModel.resultsHistory.collectAsState()
                var showHistory by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                        .padding(16.dp)
                ) {
                    // Bouton pour basculer l'affichage de l'historique

                     // Afficher l'historique des résultats
                        ClassificationHistoryPanel(
                            resultsHistory = resultsHistory,
                            onClearHistory = viewModel::clearHistory
                        )

                    // Les contrôles de la caméra restent les mêmes
                    BottomControlsPanel(
                        context = context,
                        imageCapture = imageCaptureRef.value,
                        modelIndex = modelIndex,
                        delegateIndex = delegateIndex,
                        isGpuSupported = isGpuSupported,
                        isNnapiSupported = isNnapiSupported,
                        onModelSelected = viewModel::updateModel,
                        onDelegateSelected = viewModel::updateDelegate,
                        onPhotoCaptured = { uri -> capturedPhotoUri = uri }
                    )
                }
            }
        }

        !permissionsState.shouldShowRationale && permissionsState.revokedPermissions.isNotEmpty() -> {
            PermissionPrompt(context = context)
        }

        else -> { CameraScreenError(permissionsState = permissionsState) }
    }
}
