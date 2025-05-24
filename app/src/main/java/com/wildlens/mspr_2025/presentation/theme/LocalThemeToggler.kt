package com.wildlens.mspr_2025.presentation.theme

import androidx.compose.runtime.compositionLocalOf

val LocalToggleTheme = compositionLocalOf<() -> Unit> {
    error("No theme toggler provided")
}
