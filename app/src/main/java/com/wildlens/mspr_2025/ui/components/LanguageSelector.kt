package com.wildlens.mspr_2025.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.wildlens.mspr_2025.core.langage.AppLanguage
import com.wildlens.mspr_2025.core.langage.setAppLanguage
import com.wildlens.mspr_2025.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = stringResource(id = R.string.select_language),
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(id = R.string.select_language)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            AppLanguage.entries.forEach { language ->
                DropdownMenuItem(
                    text = { Text(stringResource(id = language.labelRes)) },
                    onClick = {
                        setAppLanguage(context, language)
                        expanded = false
                    }
                )
            }
        }
    }
}
