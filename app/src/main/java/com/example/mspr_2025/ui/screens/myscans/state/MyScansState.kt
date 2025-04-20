package com.example.mspr_2025.ui.screens.myscans.state

sealed class MyScansState {
    object Loading : MyScansState()
    data class Success(val message: String) : MyScansState()
    data class Error(val message: String) : MyScansState()
}
