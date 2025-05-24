package com.wildlens.mspr_2025

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.wildlens.mspr_2025.domain.navigation.MainGraph
import com.wildlens.mspr_2025.domain.navigation.StartDestinationProvider
import com.wildlens.mspr_2025.presentation.screens.accessibility.state.AccessibilityViewModel
import com.wildlens.mspr_2025.presentation.theme.LocalToggleTheme
import com.wildlens.mspr_2025.presentation.theme.MSPR_2025Theme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var startDestinationProvider: StartDestinationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val viewModel: AccessibilityViewModel = hiltViewModel()
            val settingsUiState by viewModel.settingsUiState.collectAsState()
            val systemInDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemInDarkTheme) }
            val startDestination = startDestinationProvider.getStartDestination()

            CompositionLocalProvider(
                LocalToggleTheme provides { isDarkTheme = !isDarkTheme }
            ) {
                MSPR_2025Theme(
                    darkTheme = isDarkTheme,
                    highContrastMode = settingsUiState.highContrast
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        MainGraph(
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}
