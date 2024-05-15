package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class MagicaItem(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("tipo_magia") val tipo_magia: String,
    @SerializedName("efecto") val efecto: String,
    @SerializedName("imagen") val imagen: String
)
