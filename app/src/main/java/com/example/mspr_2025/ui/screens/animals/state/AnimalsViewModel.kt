package com.example.mspr_2025.ui.screens.animals.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mspr_2025.data.repository.AnimalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor(
    private val repository: AnimalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimalsState>(AnimalsState.Loading)
    val uiState: StateFlow<AnimalsState> = _uiState

    init {
        loadAnimals()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            try {
                val animals = repository.getAnimals()
                _uiState.value = AnimalsState.Success(animals)
            } catch (e: Exception) {
                Log.e("AnimalsVM", "Erreur parsing JSON", e)
                _uiState.value = AnimalsState.Error("Erreur de chargement : ${e.message}")
            }
        }
    }
}
