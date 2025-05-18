package com.wildlens.mspr_2025.ui.screens.iascreen.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.repository.Repository
import com.wildlens.mspr_2025.data.repository.TriggerMetaDataRepository
import com.wildlens.mspr_2025.data.repository.WildlensETLRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IAViewModel @Inject constructor(
    private val repository: Repository,
    private val wildlensETLRepository: WildlensETLRepository,
    private val triggerMetaDataRepository: TriggerMetaDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<IAState>(IAState.Loading)
    val uiState: StateFlow<IAState> = _uiState.asStateFlow()


    init {
        fetchMessage()
    }

    private fun fetchMessage() {
        viewModelScope.launch {
            repository.getMessage()
                .onStart { _uiState.value = IAState.Loading }
                .catch { _uiState.value = IAState.Error("Erreur chargement") }
                .collect { message ->
                    _uiState.value = IAState.Success(message)
                }
        }
    }
    fun triggerETL() {
        viewModelScope.launch {
            try {
                _uiState.value = IAState.Loading
                wildlensETLRepository.triggerETL()
                _uiState.value = IAState.Success("ETL déclenché avec succès")
            } catch (e: Exception) {
                _uiState.value = IAState.Error("Erreur lors du déclenchement de l'ETL: ${e.message}")
            }
        }
    }
    fun triggermetadata() {
        viewModelScope.launch {
            try {
                _uiState.value = IAState.Loading
                triggerMetaDataRepository.triggermetadatarepository()
                _uiState.value = IAState.Success("triggermetadata déclenché avec succès")
            } catch (e: Exception) {
                _uiState.value = IAState.Error("Erreur lors du déclenchement de triggermetadata: ${e.message}")
            }
        }
    }
}