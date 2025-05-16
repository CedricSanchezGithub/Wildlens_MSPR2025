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
import com.wildlens.mspr_2025.core.navigation.MainGraph
import com.wildlens.mspr_2025.core.navigation.StartDestinationProvider
import com.wildlens.mspr_2025.core.theme.LocalToggleTheme
import com.wildlens.mspr_2025.core.theme.MSPR_2025Theme
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
            val systemInDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemInDarkTheme) }
            val startDestination = startDestinationProvider.getStartDestination()

            CompositionLocalProvider(
                LocalToggleTheme provides { isDarkTheme = !isDarkTheme }
            ) {
                MSPR_2025Theme(
                    darkTheme = isDarkTheme,
                    highContrastMode = false,
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
