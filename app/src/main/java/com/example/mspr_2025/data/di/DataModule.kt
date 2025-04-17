package com.example.mspr_2025.data.di

import com.example.mspr_2025.data.repository.MessageRepository
import com.example.mspr_2025.data.repository.MessageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMessageRepository(
        impl: MessageRepositoryImpl
    ): MessageRepository = impl
}
