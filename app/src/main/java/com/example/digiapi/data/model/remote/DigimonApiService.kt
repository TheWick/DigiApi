package com.example.digiapi.data.remote

import com.example.digiapi.data.model.Digimon
import com.example.digiapi.data.model.DigimonDetail
import com.example.digiapi.data.model.DigimonLevel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DigimonApiService {

    /**
     * Obtiene lista de todos los Digimons
     * Endpoint: /api/digimon
     */
    @GET("api/digimon")
    suspend fun getAllDigimons(): Response<List<Digimon>>

    /**
     * Obtiene Digimon por nombre
     * Endpoint: /api/digimon/name/{name}
     */
    @GET("api/digimon/name/{name}")
    suspend fun getDigimonByName(
        @Path("name") name: String
    ): Response<List<DigimonDetail>>

    /**
     * Obtiene Digimons por nivel (level)
     * Endpoint: /api/digimon/level/{level}
     */
    @GET("api/digimon/level/{level}")
    suspend fun getDigimonsByLevel(
        @Path("level") level: String
    ): Response<List<Digimon>>

    /**
     * Obtiene lista de niveles disponibles
     */
    @GET("api/level")
    suspend fun getLevels(): Response<List<DigimonLevel>>

    /**
     * Búsqueda paginada (si la API lo soporta)
     */
    @GET("api/digimon")
    suspend fun getDigimonsPaginated(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<List<Digimon>>
}