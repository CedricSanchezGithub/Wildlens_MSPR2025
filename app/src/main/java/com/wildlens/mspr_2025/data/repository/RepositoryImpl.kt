package com.wildlens.mspr_2025.data.repository

import android.content.Context
import com.wildlens.mspr_2025.data.api.AnimalApiService
import com.wildlens.mspr_2025.data.api.ApiClient
import com.wildlens.mspr_2025.data.api.MetaDataApiService
import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.ImageItem
import com.wildlens.mspr_2025.data.models.MetaDataModel
import com.wildlens.mspr_2025.data.models.MetasDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class RepositoryImpl @Inject constructor() : Repository {
    override fun getMessage(): Flow<String> = flow {
        delay(1000)
        emit("Hello from the Data Layer 🐾")
    }
}

class AnimalRepositoryImpl @Inject constructor(
    private val api: AnimalApiService
) : AnimalRepository {
    override suspend fun getAnimals(): AnimalDataModel = withContext(Dispatchers.IO) {
        api.getAnimals("beaver")
    }
}

class MetaDataRepositoryImpl @Inject constructor(
    private val api: MetaDataApiService
) : MetaDataRepository {
    override suspend fun getMetaDatas(): MetasDataModel = withContext(Dispatchers.IO) {
        api.getMetaData()
    }
}
