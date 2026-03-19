package com.example.digiapi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Modelo principal de Digimon para la lista
 * Basado en la respuesta de Digimon API
 */
@Parcelize
data class Digimon(
    @SerializedName("name")
    val name: String,

    @SerializedName("img")
    val imageUrl: String,

    @SerializedName("level")
    val level: String
) : Parcelable

/**
 * Modelo para la respuesta de lista de Digimons
 */
data class DigimonResponse(
    @SerializedName("results")
    val digimons: List<Digimon>? = emptyList(),

    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null
)