package com.wildlens.mspr_2025.ui.screens.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.ui.screens.settings.state.FontScale
import com.wildlens.mspr_2025.ui.screens.settings.state.SettingsUiState
import com.wildlens.mspr_2025.ui.screens.settings.state.SettingsViewModel

@Composable
fun AccessibilityScreenSuccess(
    viewModel: SettingsViewModel,
    settings: SettingsUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Accessibilité",
            style = MaterialTheme.typography.headlineSmall
        )

        Divider()

        AccessibilitySettingToggle(
            title = "Thème sombre",
            description = "Utilise un thème sombre pour économiser l'énergie et reposer les yeux",
            checked = settings.darkTheme,
            onCheckedChange = { viewModel.toggleDarkTheme() }
        )

        AccessibilitySettingToggle(
            title = "Contraste élevé",
            description = "Améliore la lisibilité avec un contraste renforcé",
            checked = settings.highContrast,
            onCheckedChange = { viewModel.toggleHighContrast() }
        )

        AccessibilitySettingToggle(
            title = "Taille de police : ${if (settings.fontScale == FontScale.LARGE) "Grande" else "Normale"}",
            description = "Ajuste la taille de la police pour faciliter la lecture",
            checked = settings.fontScale == FontScale.LARGE,
            onCheckedChange = { viewModel.toggleFontScale() }
        )
    }
}

@Composable
private fun AccessibilitySettingToggle(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = description, style = MaterialTheme.typography.bodySmall)
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
        HorizontalDivider()
    }
}
