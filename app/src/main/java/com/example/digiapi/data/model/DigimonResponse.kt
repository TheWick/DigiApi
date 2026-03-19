package com.example.digiapi.data.model

import com.google.gson.annotations.SerializedName

/**
 * Respuesta paginada de la API de Digimon
 */
data class DigimonListResponse(
    @SerializedName("content")
    val content: List<Digimon>? = emptyList(),

    @SerializedName("pageable")
    val pageable: Pageable? = null,

    val totalElements: Int = 0,
    val totalPages: Int = 0,
    val last: Boolean = true,
    val first: Boolean = true,
    val number: Int = 0,
    val size: Int = 20,
    val empty: Boolean = false
)

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Long
)