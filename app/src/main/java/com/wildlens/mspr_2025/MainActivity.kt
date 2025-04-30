package com.wildlens.mspr_2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.wildlens.mspr_2025.core.navigation.MainGraph
import com.wildlens.mspr_2025.core.navigation.StartDestinationProvider
import com.wildlens.mspr_2025.core.theme.LocalToggleTheme
import com.wildlens.mspr_2025.core.theme.MSPR_2025Theme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var startDestinationProvider: StartDestinationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            val toggleTheme: () -> Unit = { isDarkTheme = !isDarkTheme }

            val startDestination = startDestinationProvider.getStartDestination()

            CompositionLocalProvider(LocalToggleTheme provides toggleTheme) {
                MSPR_2025Theme(darkTheme = isDarkTheme) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        MainGraph(
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}
