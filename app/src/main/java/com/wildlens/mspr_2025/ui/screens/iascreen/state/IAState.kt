package com.wildlens.mspr_2025.ui.screens.iascreen.state

sealed class IAState {
    data object Loading : IAState()
    data class Success(
        val triggeretlstatus: String? = null,
        val triggermetadatastatus: String? = null,
    ) : IAState()
    data class Error(val message: String = "") : IAState()
}
