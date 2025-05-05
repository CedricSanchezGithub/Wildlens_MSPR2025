package com.wildlens.mspr_2025.data.repository

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.ImageItem
import com.wildlens.mspr_2025.data.models.MetasDataModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface Repository {
    fun getMessage(): Flow<String>
}

interface AnimalRepository {
    suspend fun getAnimals(): AnimalDataModel
}

interface MetaDataRepository {
    suspend fun getMetaDatas(): MetasDataModel
}