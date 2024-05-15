package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class MonstruoItem(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("nivel") val nivel: Int,
    @SerializedName("atributo") val atributo: String,
    @SerializedName("invocacion") val invocacion: String,
    @SerializedName("tipo_monstruo") val tipo_monstruo: String,
    @SerializedName("efecto") val efecto: String,
    @SerializedName("ataque") val ataque: Int,
    @SerializedName("defensa") val defensa: Int,
    @SerializedName("imagen") val imagen: String
)
