package com.wildlens.mspr_2025.ui.screens.myscans.state

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.ImageItem

sealed class MyScansState {
    object Loading : MyScansState()
    data class Success(
        val imagesTracks: AnimalDataModel,
        val message: String
    ) : MyScansState()
    data class Error(val message: String) : MyScansState()
}
