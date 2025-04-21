package com.wildlens.mspr_2025.ui.screens.animals.state

import com.wildlens.mspr_2025.data.models.AnimalDataModel

sealed class AnimalsState {
    object Loading : AnimalsState()
    data class Success(val animalDataModels: List<AnimalDataModel>) : AnimalsState()
    data class Error(val message: String) : AnimalsState()
}
