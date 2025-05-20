package com.wildlens.mspr_2025.ui.screens.myscans.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.repository.AnimalTracksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyScansViewModel @Inject constructor(
    private val animalTracksRepository: AnimalTracksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MyScansState>(MyScansState.Loading)
    val uiState: StateFlow<MyScansState> = _uiState.asStateFlow()

    init {
        fetchMessage()
    }

    private fun fetchMessage() {
        _uiState.value = MyScansState.Success(
            message = "Hello depuis les scans !",
            imagesTracks = AnimalDataModel(
                description = "",
                espece = "",
                images = emptyList(),
                localisation = "",
                nomFr = "",
                nomLatin = "",
                nombreImage = 0,
                populationEstimee = 0.0
            )
        )
    }

    fun onAnimalSelected(animal: String) {
        viewModelScope.launch {
            try {
                val tracks = animalTracksRepository.getAnimalTracks(animal)
                _uiState.value = MyScansState.Loading
                _uiState.value = MyScansState.Success(
                    message = "Images pour $animal chargées",
                    imagesTracks = tracks
                )
            } catch (e: Exception) {
                _uiState.value = MyScansState.Error("Erreur : ${e.message}")
            }
        }
    }
}