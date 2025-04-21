package com.wildlens.mspr_2025.data.repository

import com.wildlens.mspr_2025.data.models.UserDataModel

interface UserRepository {
    suspend fun createUser(user: UserDataModel): Result<Unit>
    suspend fun getUser(uid: String): Result<UserDataModel>
}
