package com.wildlens.mspr_2025.presentation.screens.scans.state

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.Species

sealed class MyScansScreenState {
    data object Loading : MyScansScreenState()
    data class Success(val data: MyScansScreenData) : MyScansScreenState()
    data class Error(val message: String) : MyScansScreenState()
}

data class MyScansScreenData(
    val message: String = "",
    val speces: Species = Species(emptyList()),
    val imagesTracks: AnimalDataModel? = null
)
