package com.wildlens.mspr_2025.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TriggerResponse(
    val message: String,
    val status: String
)