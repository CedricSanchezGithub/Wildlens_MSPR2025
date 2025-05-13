package com.wildlens.mspr_2025.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wildlens.mspr_2025.data.api.AnimalApiService
import com.wildlens.mspr_2025.data.repository.*
import com.google.firebase.firestore.FirebaseFirestore
import com.wildlens.mspr_2025.data.api.AnimalTracksApiService
import com.wildlens.mspr_2025.data.api.MetaDataApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBaseUrl(): String = "http://192.168.1.26:5000/"

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimalApiService(retrofit: Retrofit): AnimalApiService =
        retrofit.create(AnimalApiService::class.java)

    @Provides
    fun provideMessageRepository(
        impl: RepositoryImpl
    ): Repository = impl

    @Provides
    @Singleton
    fun provideAnimalRepository(
        impl: AnimalRepositoryImpl
    ): AnimalRepository = impl

    @Provides
    @Singleton
    fun provideAnimaltracksRepository(
        impl: AnimalTracksRepositoryImpl
    ): AnimalTracksRepository = impl

    @Provides
    @Singleton
    fun provideMetaDataRepository(
        impl: MetaDataRepositoryImpl
    ): MetaDataRepository = impl

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(firestore)


    @Provides
    @Singleton
    fun provideMetaDataApiService(retrofit: Retrofit): MetaDataApiService =
        retrofit.create(MetaDataApiService::class.java)

    @Provides
    @Singleton
    fun provideAnimalTracksApiService(retrofit: Retrofit): AnimalTracksApiService =
        retrofit.create(AnimalTracksApiService::class.java)
}
