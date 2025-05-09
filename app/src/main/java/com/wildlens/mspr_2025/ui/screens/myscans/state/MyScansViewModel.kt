package com.wildlens.mspr_2025.ui.screens.myscans.state

import android.R.id.message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.repository.AnimalTracksRepository
import com.wildlens.mspr_2025.data.repository.Repository
import com.wildlens.mspr_2025.ui.screens.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyScansViewModel @Inject constructor(
    private val repository: Repository,
    private val animalTracksRepository: AnimalTracksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MyScansState>(MyScansState.Loading)
    val uiState: StateFlow<MyScansState> = _uiState.asStateFlow()

    init {
        fetchMessage()
        fetchAnimalsTracks()
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

    private fun fetchAnimalsTracks() {
        viewModelScope.launch {
            try {
                val tracks = animalTracksRepository.getAnimalTracks()
                _uiState.value = MyScansState.Success(
                    message = "Images chargées avec succès",
                    imagesTracks = tracks
                )
            } catch (e: Exception) {
                _uiState.value = MyScansState.Error("Erreur de chargement : ${e.message}")
            }
        }
    }
}
