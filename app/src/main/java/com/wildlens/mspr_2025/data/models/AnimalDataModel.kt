package com.wildlens.mspr_2025.data.models

import kotlinx.serialization.Serializable

//@Serializable
//data class AnimalDataModel(
//    @SerialName("idEspece") val id: Int,
//    @SerialName("famille") val family: String,
//    @SerialName("nomLatin") val latinName: String,
//    @SerialName("nomFr") val frenchName: String,
//    @SerialName("nomEn") val englishName: String,
//    @SerialName("description") val description: String,
//    @SerialName("populationEstimee") val estimatedPopulation: Double,
//    @SerialName("localisation") val location: String,
//    @SerialName("nombreImage") val imageCount: Int,
//    @SerialName("coeffMultiplication") val multiplicationCoeff: Int
//)

@Serializable
data class AnimalDataModel(
    val description: String,
    val espece: String,
    val images: List<ImageItem>,
    val localisation: String,
    val nomFr: String,
    val nomLatin: String,
    val nombreImage: Int,
    val populationEstimee: Double
)

@Serializable
data class ImageItem(
    val path: String,
    val url: String
)
