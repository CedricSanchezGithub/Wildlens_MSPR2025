package com.wildlens.mspr_2025.data.repository

import android.util.Log
import com.wildlens.mspr_2025.data.api.*
import com.wildlens.mspr_2025.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {
    override fun getMessage(): Flow<String> = flow {
        delay(1000)
        emit(" Bienvenue sur Wildlens")
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

class AnimalTracksRepositoryImpl @Inject constructor(
    private val api: AnimalTracksApiService
) : AnimalTracksRepository {
    override suspend fun getAnimalTracks(animal: String): AnimalDataModel = withContext(Dispatchers.IO) {
        api.getAnimalTracks(animal)
    }
}

class WildlensETLRepositoryImpl @Inject constructor(
    private val api: WildlensETLApiService
) : WildlensETLRepository {
    override suspend fun triggerETL() : TriggerResponse = withContext(Dispatchers.IO) {
        api.triggerETL()
    }
}

class WildlensMetaDataRepositoryImpl @Inject constructor(
    private val api: WildlensMetadataApiService
) : WildlensMetaDataRepository {
    override suspend fun triggermetadata() : TriggerResponse = withContext(Dispatchers.IO) {
        api.triggerMetadata()
    }
}

class WildlensSpeciesListRepositoryImpl @Inject constructor(
    private val api: WildlensSpeciesListApiService
) : WildlensSpeciesListRepository {
    override suspend fun getSpeciesList(): Species = withContext(Dispatchers.IO) {
        api.getSpeciesList()
    }
}

class ImageUploadRepositoryImpl @Inject constructor(
    private val api: ImageUploadApiService
) : ImageUploadRepository {
    override suspend fun uploadImage(file: File, classification: String): UploadResponse? = withContext(Dispatchers.IO) {

        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val classificationBody = classification.toRequestBody("text/plain".toMediaTypeOrNull())

        return@withContext try {
            api.uploadImage(imagePart, classificationBody)
        } catch (e: Exception) {
            Log.e("UploadError", e.message ?: "Erreur inconnue")
            null
        }
    }
}


