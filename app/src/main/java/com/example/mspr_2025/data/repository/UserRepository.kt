package com.example.mspr_2025.data.repository

import com.example.mspr_2025.data.models.UserDataModel

interface UserRepository {
    suspend fun createUser(user: UserDataModel): Result<Unit>
    suspend fun getUser(uid: String): Result<UserDataModel>
}
