package com.example.mspr_2025.data.di

import com.example.mspr_2025.data.repository.AnimalRepository
import com.example.mspr_2025.data.repository.AnimalRepositoryImpl
import com.example.mspr_2025.data.repository.Repository
import com.example.mspr_2025.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMessageRepository(
        impl: RepositoryImpl
    ): Repository = impl

    @Provides
    @Singleton
    fun provideAnimalRepository(
        impl: AnimalRepositoryImpl
    ): AnimalRepository = impl
}
