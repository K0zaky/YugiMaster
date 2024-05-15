package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class TrampaItem(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("tipo_trampa") val tipo_trampa: String,
    @SerializedName("efecto") val efecto: String,
    @SerializedName("imagen") val imagen: String
)
