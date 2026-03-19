package com.example.digiapi.data.repository

import com.example.digiapi.data.model.Digimon
import com.example.digiapi.data.model.DigimonDetail
import com.example.digiapi.data.remote.DigimonApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DigimonRepository @Inject constructor(
    private val apiService: DigimonApiService
) {

    suspend fun getAllDigimons(): Result<List<Digimon>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllDigimons()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDigimonByName(name: String): Result<DigimonDetail?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDigimonByName(name)
            if (response.isSuccessful) {
                Result.success(response.body()?.firstOrNull())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDigimonsByLevel(level: String): Result<List<Digimon>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDigimonsByLevel(level)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}