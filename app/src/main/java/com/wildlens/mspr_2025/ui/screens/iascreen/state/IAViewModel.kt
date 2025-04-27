package com.wildlens.mspr_2025.ui.screens.iascreen.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IAViewModel @Inject constructor(
    private val repository: Repository
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
}