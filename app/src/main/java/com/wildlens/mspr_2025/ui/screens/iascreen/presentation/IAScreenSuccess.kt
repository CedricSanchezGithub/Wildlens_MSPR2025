package com.wildlens.mspr_2025.ui.screens.iascreen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.core.theme.LocalToggleTheme
import com.wildlens.mspr_2025.ui.screens.iascreen.state.IAState
import com.wildlens.mspr_2025.ui.screens.iascreen.state.IAViewModel

@Composable
fun IAScreenSuccess(
    uiModel: IAState.Success,
    viewModel: IAViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.SmartToy,
            contentDescription = "Robot Icon"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.etl_model_info))
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.triggerETL() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.start_etl))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.triggermetadata() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.start_ia))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.triggermetadata() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.start_metadata))
        }
    }
}