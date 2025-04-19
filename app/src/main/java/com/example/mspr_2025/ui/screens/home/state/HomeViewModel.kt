package com.example.mspr_2025.ui.screens.home.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mspr_2025.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeState<String>>(HomeState.Loading)
    val uiState: StateFlow<HomeState<String>> = _uiState.asStateFlow()


    init {
        fetchMessage()
    }

    private fun fetchMessage() {
        viewModelScope.launch {
            repository.getMessage()
                .onStart { _uiState.value = HomeState.Loading }
                .catch { _uiState.value = HomeState.Error("Erreur chargement") }
                .collect { message ->
                    _uiState.value = HomeState.Success(message)
                }
        }
    }
}
