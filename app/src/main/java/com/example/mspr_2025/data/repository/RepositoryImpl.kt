package com.example.mspr_2025.data.repository

import android.content.Context
import com.example.mspr_2025.data.models.Animal
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
    @ApplicationContext private val context: Context
) : AnimalRepository {

    override suspend fun getAnimals(): List<Animal> = withContext(Dispatchers.IO) {
        val json = context.assets.open("getmetadata.json").bufferedReader().use { it.readText() }
        Json.decodeFromString(json)
    }
}