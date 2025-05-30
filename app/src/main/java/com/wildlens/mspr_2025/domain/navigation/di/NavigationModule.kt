package com.wildlens.mspr_2025.domain.navigation.di

import com.wildlens.mspr_2025.domain.navigation.StartDestinationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module Hilt qui fournit les composants nécessaires à la navigation dans l'application.
 *
 * - `StartDestinationProvider` : responsable de déterminer dynamiquement la route de démarrage
 *   en fonction de l'état utilisateur (FirebaseAuth, préférences locales, etc.).
 *
 * - `AppRouter` : routeur central de l'application, basé sur un StateFlow,
 *   permettant une navigation déclarative et testable.
 */
@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    /**
     * Fournit une instance unique de StartDestinationProvider.
     */
    @Provides
    @Singleton
    fun provideStartDestinationProvider(): StartDestinationProvider = StartDestinationProvider()

}