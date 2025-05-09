package com.wildlens.mspr_2025.data.repository

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.MetasDataModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMessage(): Flow<String>
}

interface AnimalRepository {
    suspend fun getAnimals(): AnimalDataModel
}

interface MetaDataRepository {
    suspend fun getMetaDatas(): MetasDataModel
}

interface AnimalTracksRepository {
    suspend fun getAnimalTracks(animal: String): AnimalDataModel
}