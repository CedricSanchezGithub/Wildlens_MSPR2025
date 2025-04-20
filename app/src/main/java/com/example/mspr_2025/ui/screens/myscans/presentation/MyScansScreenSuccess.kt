package com.example.mspr_2025.ui.screens.myscans.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mspr_2025.ui.screens.myscans.state.MyScansState

@Composable
fun MyScansScreenSuccess(uiModel: MyScansState.Success) {
    Text(text = uiModel.message)
}
