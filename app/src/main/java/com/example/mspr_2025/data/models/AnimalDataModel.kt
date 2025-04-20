package com.example.mspr_2025.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimalDataModel(
    @SerialName("idEspece") val id: Int,
    @SerialName("famille") val family: String,
    @SerialName("nomLatin") val latinName: String,
    @SerialName("nomFr") val frenchName: String,
    @SerialName("nomEn") val englishName: String,
    @SerialName("description") val description: String,
    @SerialName("populationEstimee") val estimatedPopulation: Double,
    @SerialName("localisation") val location: String,
    @SerialName("nombreImage") val imageCount: Int,
    @SerialName("coeffMultiplication") val multiplicationCoeff: Int
)
