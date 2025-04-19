package com.example.mspr_2025.ui.screens.animals.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mspr_2025.ui.screens.animals.state.AnimalsAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimalsViewModel : ViewModel() {

    private val _state = MutableStateFlow<AnimalsState>(AnimalsState.Loading)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onAction(action: AnimalsAction) {
        when (action) {
            AnimalsAction.OnRefresh -> loadData()
        }
    }

    private fun loadData() {
        _state.value = AnimalsState.Loading
        viewModelScope.launch {
            delay(1000)
            _state.value = AnimalsState.Success("Bienvenue depuis Animals !")
        }
    }
}
