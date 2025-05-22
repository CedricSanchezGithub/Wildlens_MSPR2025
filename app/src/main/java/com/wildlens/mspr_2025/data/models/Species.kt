package com.wildlens.mspr_2025.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Species (
    @SerialName("especes")
    val species: List<String>
)

