package com.wildlens.mspr_2025.ui.screens.profile.state

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
class ProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()


    init {
        fetchMessage()
    }

    private fun fetchMessage() {
        viewModelScope.launch {
            repository.getMessage()
                .onStart { _uiState.value = ProfileState.Loading }
                .catch { _uiState.value = ProfileState.Error("Erreur chargement") }
                .collect { message ->
                    _uiState.value = ProfileState.Success(message)
                }
        }
    }
}