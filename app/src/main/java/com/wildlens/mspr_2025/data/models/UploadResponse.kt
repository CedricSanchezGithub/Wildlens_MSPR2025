package com.wildlens.mspr_2025.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    val success: Boolean,
    val message: String,
    val filename: String?
)
