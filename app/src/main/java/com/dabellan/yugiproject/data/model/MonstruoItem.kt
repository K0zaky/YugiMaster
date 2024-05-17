package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class MonstruoItem(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("nivel") val nivel: Int = 0,
    @SerializedName("atributo") val atributo: String = "",
    @SerializedName("invocacion") val invocacion: String = "",
    @SerializedName("tipoMonstruo") val tipoMonstruo: String = "",
    @SerializedName("efecto") val efecto: String = "",
    @SerializedName("ataque") val ataque: Int = 0,
    @SerializedName("defensa") val defensa: Int = 0,
    @SerializedName("imagen") val imagen: String = ""
)