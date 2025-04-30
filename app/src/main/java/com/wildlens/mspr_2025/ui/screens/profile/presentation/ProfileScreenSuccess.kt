package com.wildlens.mspr_2025.ui.screens.profile.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.wildlens.mspr_2025.ui.screens.profile.state.ProfileState

@Composable
fun ProfileScreenSuccess(uiModel: ProfileState.Success) {
    Text(text = uiModel.message)
}
