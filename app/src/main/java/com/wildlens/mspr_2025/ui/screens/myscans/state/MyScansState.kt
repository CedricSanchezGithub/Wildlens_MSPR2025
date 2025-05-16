package com.wildlens.mspr_2025.ui.screens.myscans.state

import com.wildlens.mspr_2025.data.models.AnimalDataModel

sealed class MyScansState {
    data object Loading : MyScansState()
    data class Success(
        val imagesTracks: AnimalDataModel,
        val message: String
    ) : MyScansState()
    data class Error(val message: String) : MyScansState()
}
