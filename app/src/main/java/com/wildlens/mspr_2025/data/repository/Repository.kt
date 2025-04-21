package com.wildlens.mspr_2025.data.repository

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMessage(): Flow<String>
}

interface AnimalRepository {
    suspend fun getAnimals(): List<AnimalDataModel>
}
