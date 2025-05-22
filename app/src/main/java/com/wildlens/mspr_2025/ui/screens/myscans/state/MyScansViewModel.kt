package com.wildlens.mspr_2025.ui.screens.myscans.state

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
class MyScansViewModel @Inject constructor(
    private val animalTracksRepository: AnimalTracksRepository,
    private val wildlensSpeciesListRepository: WildlensSpeciesListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MyScansState>(MyScansState.Loading)
    val uiState: StateFlow<MyScansState> = _uiState.asStateFlow()

    init {
        fetchSpecies()
    }

    private fun fetchSpecies() {
        viewModelScope.launch {
            _uiState.value = MyScansState.Loading
            runCatching {
                wildlensSpeciesListRepository.getSpeciesList()
            }.onSuccess { species ->
                _uiState.value = MyScansState.Success(
                    MyScansData(message = "Espèces chargées", speces = species)
                )
            }.onFailure { e ->
                _uiState.value = MyScansState.Error("Erreur : ${e.message}")
            }
        }
    }

    fun onAnimalSelected(animal: String) {
        viewModelScope.launch {
            _uiState.value = MyScansState.Loading
            runCatching {
                animalTracksRepository.getAnimalTracks(animal)
            }.onSuccess { animalData ->
                _uiState.value = MyScansState.Success(
                    MyScansData(message = "Images chargées pour $animal", imagesTracks = animalData)
                )
            }.onFailure { e ->
                _uiState.value = MyScansState.Error("Erreur : ${e.message}")
            }
        }
    }
}
