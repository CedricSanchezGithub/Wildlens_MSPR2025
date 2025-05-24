package com.wildlens.mspr_2025.presentation.screens.profile.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.wildlens.mspr_2025.presentation.screens.profile.state.ProfileState

@Composable
fun ProfileScreenSuccess(uiModel: ProfileState.Success) {
    Text(text = uiModel.message)
}
