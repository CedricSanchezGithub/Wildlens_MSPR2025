package com.example.mspr_2025.data.repository

import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MessageRepositoryImpl @Inject constructor() : MessageRepository {
    override fun getMessage(): Flow<String> = flow {
        delay(1000)
        emit("Hello from the Data Layer 🐾")
    }
}
