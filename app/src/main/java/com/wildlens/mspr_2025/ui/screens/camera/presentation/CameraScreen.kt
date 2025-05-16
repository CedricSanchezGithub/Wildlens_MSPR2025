package com.wildlens.mspr_2025.ui.screens.camera.presentation

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.ui.screens.camera.presentation.components.BottomControlsPanel
import com.wildlens.mspr_2025.ui.screens.camera.presentation.components.CameraLoadingOverlay
import com.wildlens.mspr_2025.ui.screens.camera.presentation.components.CapturedThumbnail
import com.wildlens.mspr_2025.ui.screens.camera.presentation.components.PermissionPrompt
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

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        inferenceTime?.let {
                            Text(
                                stringResource(R.string.inference_time, it),                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        val categories = results.firstOrNull()?.categories ?: emptyList()
                        LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                            items(categories, key = { it.label }) { category ->
                                Text(
                                    text = stringResource(
                                        R.string.label_with_score,
                                        category.label,
                                        (category.score * 100).toInt()
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }
                    }
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
