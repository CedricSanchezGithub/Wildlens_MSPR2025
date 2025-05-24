package com.wildlens.mspr_2025.data.api

import com.wildlens.mspr_2025.data.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

interface WildlensETLApiService {
    @GET("triggermspr")
    suspend fun triggerETL(): TriggerResponse
}

interface WildlensMetadataApiService {
    @GET("triggermetadata")
    suspend fun triggerMetadata(): TriggerResponse
}

interface WildlensSpeciesListApiService {
    @GET("api/especes")
    suspend fun getSpeciesList(): Species
}

interface ImageUploadApiService {
    @Multipart
    @POST("photo_download")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("classification") classification: RequestBody
    ): UploadResponse

}
