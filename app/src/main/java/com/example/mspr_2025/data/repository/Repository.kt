package com.example.mspr_2025.data.repository

import com.example.mspr_2025.data.models.Animal
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMessage(): Flow<String>
}

interface AnimalRepository {
    suspend fun getAnimals(): List<Animal>
}
