package com.wildlens.mspr_2025.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.MetaDataModel
import com.wildlens.mspr_2025.data.models.MetasDataModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.java

object ApiClient {
    private const val BASE_URL = "http://192.168.1.26:5000/"

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    val apiService: AnimalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AnimalApiService::class.java)
    }
}

interface AnimalApiService {
    @GET("api/images")
    suspend fun getAnimals(@Query("espece") espece: String): AnimalDataModel
}

interface MetaDataApiService {
    @GET("api/metadata")
    suspend fun getMetaData(): MetasDataModel
}