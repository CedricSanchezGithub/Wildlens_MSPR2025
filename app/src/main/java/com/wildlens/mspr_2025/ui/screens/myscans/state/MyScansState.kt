package com.wildlens.mspr_2025.ui.screens.myscans.state

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.Species

sealed class MyScansState {
    data object Loading : MyScansState()
    data class Success(val data: MyScansData) : MyScansState()
    data class Error(val message: String) : MyScansState()
}

data class MyScansData(
    val message: String = "",
    val speces: Species = Species(emptyList()),
    val imagesTracks: AnimalDataModel? = null
)
