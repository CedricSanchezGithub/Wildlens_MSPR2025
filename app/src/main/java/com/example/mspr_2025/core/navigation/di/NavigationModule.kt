package com.example.mspr_2025.core.navigation.di

import com.example.mspr_2025.core.navigation.AppRouter
import com.example.mspr_2025.core.navigation.StartDestinationProvider
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

    /**
     * Fournit une instance unique de AppRouter, initialisée avec la route déterminée
     * dynamiquement au démarrage de l'application.
     */
    @Provides
    @Singleton
    fun provideAppRouter(
        startDestinationProvider: StartDestinationProvider
    ): AppRouter {
        val initialRoute = startDestinationProvider.getStartDestination()
        return AppRouter(initialRoute)
    }
}