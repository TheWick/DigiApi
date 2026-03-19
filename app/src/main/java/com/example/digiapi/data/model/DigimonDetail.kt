package com.example.digiapi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Modelo detallado de Digimon con información completa
 */
@Parcelize
data class DigimonDetail(
    @SerializedName("name")
    val name: String,

    @SerializedName("img")
    val imageUrl: String,

    @SerializedName("level")
    val level: String,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("attribute")
    val attribute: String? = null,

    @SerializedName("field")
    val field: String? = null,

    @SerializedName("profile")
    val description: String? = null,

    @SerializedName("evolution")
    val evolutions: List<Evolution>? = emptyList(),

    @SerializedName("priorEvolution")
    val priorEvolutions: List<Evolution>? = emptyList()
) : Parcelable

@Parcelize
data class Evolution(
    @SerializedName("digimon")
    val digimonName: String,

    @SerializedName("condition")
    val condition: String,

    @SerializedName("img")
    val imageUrl: String? = null
) : Parcelable

/**
 * Modelo para tipos/levels de Digimon (para filtros)
 */
data class DigimonLevel(
    @SerializedName("level")
    val level: String
)

data class DigimonType(
    @SerializedName("type")
    val type: String
)