package com.wildlens.mspr_2025.presentation.screens.scans.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.repository.AnimalTracksRepository
import com.wildlens.mspr_2025.data.repository.WildlensSpeciesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScansScreenViewModel @Inject constructor(
    private val animalTracksRepository: AnimalTracksRepository,
    private val wildlensSpeciesListRepository: WildlensSpeciesListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MyScansScreenState>(MyScansScreenState.Loading)
    val uiState: StateFlow<MyScansScreenState> = _uiState.asStateFlow()

    init {
        fetchSpecies()
    }

    private fun fetchSpecies() {
        viewModelScope.launch {
            _uiState.value = MyScansScreenState.Loading
            runCatching {
                wildlensSpeciesListRepository.getSpeciesList()
            }.onSuccess { species ->
                _uiState.value = MyScansScreenState.Success(
                    MyScansScreenData(message = "Espèces chargées", speces = species)
                )
            }.onFailure { e ->
                _uiState.value = MyScansScreenState.Error("Erreur : ${e.message}")
            }
        }
    }

    fun onAnimalSelected(animal: String) {
        viewModelScope.launch {
            _uiState.value = MyScansScreenState.Loading
            runCatching {
                animalTracksRepository.getAnimalTracks(animal)
            }.onSuccess { animalData ->
                _uiState.value = MyScansScreenState.Success(
                    MyScansScreenData(message = "Images chargées pour $animal", imagesTracks = animalData)
                )
            }.onFailure { e ->
                _uiState.value = MyScansScreenState.Error("Erreur : ${e.message}")
            }
        }
    }
}
