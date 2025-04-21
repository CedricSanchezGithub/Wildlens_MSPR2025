package com.wildlens.mspr_2025.data.repository

import com.wildlens.mspr_2025.data.models.UserDataModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun createUser(user: UserDataModel): Result<Unit> = try {
        firestore.collection("users")
            .document(user.uid)
            .set(user)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getUser(uid: String): Result<UserDataModel> = try {
        val doc = firestore.collection("users")
            .document(uid)
            .get()
            .await()

        val user = doc.toObject(UserDataModel::class.java)
        if (user != null) {
            Result.success(user)
        } else {
            Result.failure(Exception("User not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
