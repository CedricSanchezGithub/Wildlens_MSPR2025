package com.example.mspr_2025.data.di

import com.example.mspr_2025.data.repository.AnimalRepository
import com.example.mspr_2025.data.repository.AnimalRepositoryImpl
import com.example.mspr_2025.data.repository.Repository
import com.example.mspr_2025.data.repository.RepositoryImpl
import com.example.mspr_2025.data.repository.UserRepository
import com.example.mspr_2025.data.repository.UserRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(firestore)
}
