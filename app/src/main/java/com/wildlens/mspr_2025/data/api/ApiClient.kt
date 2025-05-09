package com.wildlens.mspr_2025.data.api

import com.wildlens.mspr_2025.data.models.AnimalDataModel
import com.wildlens.mspr_2025.data.models.ImageItem
import com.wildlens.mspr_2025.data.models.MetasDataModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimalApiService {
    @GET("api/images")
    suspend fun getAnimals(@Query("espece") espece: String): AnimalDataModel
}

interface MetaDataApiService {
    @GET("api/metadata")
    suspend fun getMetaData(): MetasDataModel
}

interface AnimalTracksApiService {
    @GET("api/images")
    suspend fun getAnimalTracks(@Query("espece") espece: String): AnimalDataModel
}