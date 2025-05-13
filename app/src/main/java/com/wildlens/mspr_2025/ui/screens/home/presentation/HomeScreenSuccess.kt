package com.wildlens.mspr_2025.ui.screens.home.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wildlens.mspr_2025.R

@Composable
fun HomeScreenSuccess(
    text: String,
){
    Text(text = stringResource(R.string.welcome_message))
    Text(text = text)
}

