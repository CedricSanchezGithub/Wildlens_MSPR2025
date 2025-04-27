package com.wildlens.mspr_2025.ui.screens.iascreen.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.ui.screens.iascreen.state.IAState

@Composable
fun IAScreenSuccess(uiModel: IAState.Success) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.SmartToy,
            contentDescription = "Robot Icon"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Informations sur la pipeline ETL et le modèle de machine learning :)")
    }
}