package com.wildlens.mspr_2025.presentation.screens.accessibility.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.presentation.langage.AppLanguage
import com.wildlens.mspr_2025.presentation.langage.setAppLanguage
import com.wildlens.mspr_2025.presentation.screens.accessibility.state.AccessibilityViewModel
import com.wildlens.mspr_2025.presentation.screens.accessibility.state.SettingsUiState
import com.wildlens.mspr_2025.presentation.theme.LocalToggleTheme

@Composable
fun AccessibilityScreenSuccess(
    viewModel: AccessibilityViewModel,
    settings: SettingsUiState
) {

    val toggleTheme = LocalToggleTheme.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.accessibility),
            style = MaterialTheme.typography.headlineSmall
        )

        HorizontalDivider()
        AccessibilitySettingToggle(
            title = stringResource(R.string.high_contrast),
            description = stringResource(R.string.high_contrast_desc),
            checked = settings.highContrast,
            onCheckedChange = { viewModel.toggleHighContrast() }
        )

        AccessibilitySettingToggle(
            title = stringResource(R.string.dark_theme),
            description = stringResource(R.string.dark_theme_desc),
            checked = settings.darkTheme,
            onCheckedChange = {
                viewModel.toggleDarkTheme()
                toggleTheme()
            }
        )

        LanguageSettingChoise(
            title = stringResource(R.string.language),
            description = stringResource(R.string.language_desc),
            selectedLanguage = settings.language,
            onLanguageChange = { viewModel.changeLanguage(it) }
        )
    }

}

@Composable
private fun LanguageSettingChoise(
    title: String,
    description: String,
    selectedLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = description, style = MaterialTheme.typography.bodySmall)
            }

            TextButton(onClick = { expanded = true }) {
                Text(stringResource(id = selectedLanguage.labelRes))
            }
        }

        if (expanded) {
            AlertDialog(
                onDismissRequest = { expanded = false },
                confirmButton = {
                    TextButton(onClick = { expanded = false }) {
                        Text(stringResource(android.R.string.cancel))
                    }
                },
                title = { Text(stringResource(R.string.language)) },
                text = {
                    Column {
                        AppLanguage.entries.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(stringResource(id = language.labelRes)) },
                                onClick = {
                                    onLanguageChange(language)
                                    setAppLanguage(context, language)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            )
        }

        HorizontalDivider()
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
