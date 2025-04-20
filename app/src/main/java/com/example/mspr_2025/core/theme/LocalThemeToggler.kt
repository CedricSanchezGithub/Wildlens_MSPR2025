package com.example.mspr_2025.core.theme

import androidx.compose.runtime.compositionLocalOf

val LocalToggleTheme = compositionLocalOf<() -> Unit> {
    error("No theme toggler provided")
}
