package com.example.mspr_2025.ui.screens.animals.state

sealed class AnimalsState {
    object Loading : AnimalsState()
    data class Success(val message: String) : AnimalsState()
    data class Error(val message: String) : AnimalsState()
}