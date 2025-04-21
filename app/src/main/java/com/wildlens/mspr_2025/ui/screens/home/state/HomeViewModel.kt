package com.wildlens.mspr_2025.ui.screens.home.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildlens.mspr_2025.data.models.UserDataModel
import com.wildlens.mspr_2025.data.repository.Repository
import com.wildlens.mspr_2025.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
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
    private val repository: Repository,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeState<String>>(HomeState.Loading)
    val uiState: StateFlow<HomeState<String>> = _uiState.asStateFlow()

    private val _user = MutableStateFlow<UserDataModel?>(null)
    val user: StateFlow<UserDataModel?> = _user

    init {
        fetchMessage()
        val uid = auth.currentUser?.uid
        if (uid != null) {
            viewModelScope.launch {
                val result = userRepository.getUser(uid)
                if (result.isSuccess) {
                    _user.value = result.getOrNull()
                }
            }
        }
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
