package com.wildlens.mspr_2025.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wildlens.mspr_2025.data.repository.*
import com.google.firebase.firestore.FirebaseFirestore
import com.wildlens.mspr_2025.data.api.*
import com.wildlens.mspr_2025.domain.session.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val apiKey = ""
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(apiKey))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideBaseUrl(): String = "http://90.51.140.217:5001/"

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
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

    @Provides
    @Singleton
    fun provideWildlensETLRepository(
        impl: WildlensETLRepositoryImpl
    ): WildlensETLRepository = impl

    @Provides
    @Singleton
    fun provideWildlensETLApiService(retrofit: Retrofit): WildlensETLApiService =
        retrofit.create(WildlensETLApiService::class.java)

    @Provides
    @Singleton
    fun provideTriggerMetaDataRepository(
        impl: WildlensMetaDataRepositoryImpl
    ): WildlensMetaDataRepository = impl

    @Provides
    @Singleton
    fun provideTriggerMetaDataApiService(retrofit: Retrofit): WildlensMetadataApiService =
        retrofit.create(WildlensMetadataApiService::class.java)

    @Provides
    @Singleton
    fun provideSpeciesListRepository(
        impl: WildlensSpeciesListRepositoryImpl
    ): WildlensSpeciesListRepository = impl

    @Provides
    @Singleton
    fun provideSpeciesListApiService(retrofit: Retrofit): WildlensSpeciesListApiService =
        retrofit.create(WildlensSpeciesListApiService::class.java)

    @Provides
    @Singleton
    fun provideImageUploadApiService(retrofit: Retrofit): ImageUploadApiService =
        retrofit.create(ImageUploadApiService::class.java)

    @Provides
    @Singleton
    fun provideImageUploadRepository(
        impl: ImageUploadRepositoryImpl
    ): ImageUploadRepository = impl

}

