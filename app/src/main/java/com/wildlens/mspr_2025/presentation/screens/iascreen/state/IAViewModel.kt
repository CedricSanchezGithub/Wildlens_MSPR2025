package com.wildlens.mspr_2025.presentation.screens.iascreen.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.repository.WildlensETLRepository
import com.wildlens.mspr_2025.data.repository.WildlensMetaDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IAViewModel @Inject constructor(
    private val wildlensETLRepository: WildlensETLRepository,
    private val wildlensMetaDataRepository: WildlensMetaDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<IAState>(IAState.Loading)
    val uiState: StateFlow<IAState> = _uiState.asStateFlow()

    init {
        _uiState.value = IAState.Success()
    }

    fun triggerETL() {
        viewModelScope.launch {
            try {
                _uiState.value = IAState.Loading
                val response = wildlensETLRepository.triggerETL()
                _uiState.value = IAState.Success(response.message)
            } catch (e: Exception) {
                _uiState.value = IAState.Error("Erreur lors du déclenchement de l'ETL: ${e.message}")
            }
        }
    }

    fun triggermetadata() {
        viewModelScope.launch {
            try {
                _uiState.value = IAState.Loading
                val response = wildlensMetaDataRepository.triggermetadata()
                _uiState.value = IAState.Success(response.message)
            } catch (e: Exception) {
                _uiState.value = IAState.Error("Erreur lors du déclenchement de triggermetadata: ${e.message}")
            }
        }
    }
}