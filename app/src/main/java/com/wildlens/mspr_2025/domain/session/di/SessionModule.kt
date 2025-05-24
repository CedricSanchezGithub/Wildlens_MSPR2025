package com.wildlens.mspr_2025.domain.session.di

import com.wildlens.mspr_2025.domain.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideAuthManager(): SessionManager = SessionManager()
}
