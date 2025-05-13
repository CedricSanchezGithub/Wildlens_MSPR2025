package com.wildlens.mspr_2025.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetasDataModel(
    @SerialName("metadata")
    val metasData: List<MetaDataModel>
)

@Serializable
data class MetaDataModel(
    @SerialName("id_espece")
    val idEpece: Int,
    val description: String,
    val localisation: String,
    @SerialName("nom_en")
    val nomEn: String,
    @SerialName("nom_fr")
    val nomFr: String,
    @SerialName("nom_latin")
    val nomLatin: String,
    @SerialName("population_estimee")
    val populationEstimee: Double
)
