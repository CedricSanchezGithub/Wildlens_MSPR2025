package com.example.mspr_2025.ui.screens.myscans.state

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
class MyScansViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MyScansState>(MyScansState.Loading)
    val uiState: StateFlow<MyScansState> = _uiState.asStateFlow()


    init {
        fetchMessage()
    }

    private fun fetchMessage() {
        viewModelScope.launch {
            _uiState.value = MyScansState.Success("Hello depuis les scans !")
        }
    }
}