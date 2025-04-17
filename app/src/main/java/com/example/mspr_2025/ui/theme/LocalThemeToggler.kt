package com.example.mspr_2025.ui.theme

import androidx.compose.runtime.compositionLocalOf

val LocalToggleTheme = compositionLocalOf<() -> Unit> {
    error("No theme toggler provided")
}
