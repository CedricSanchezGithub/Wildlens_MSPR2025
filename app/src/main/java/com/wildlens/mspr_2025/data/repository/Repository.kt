package com.wildlens.mspr_2025.data.repository

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.MetasDataModel
import com.wildlens.mspr_2025.data.models.Species
import com.wildlens.mspr_2025.data.models.TriggerResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

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

interface WildlensETLRepository {
    suspend fun triggerETL(): TriggerResponse
}

interface WildlensMetaDataRepository {
    suspend fun triggermetadata() : TriggerResponse
}

interface WildlensSpeciesListRepository {
    suspend fun getSpeciesList(): Species
}

interface ImageUploadRepository {
    suspend fun uploadImage(file: File): Boolean
}


