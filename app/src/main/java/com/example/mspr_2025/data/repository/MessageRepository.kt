package com.example.mspr_2025.data.repository

import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessage(): Flow<String>
}
