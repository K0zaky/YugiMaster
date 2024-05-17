package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class TrampaItem(
    @SerializedName("id") val id: Int=0,
    @SerializedName("nombre") val nombre: String="",
    @SerializedName("tipoTrampa") val tipoTrampa: String="",
    @SerializedName("efecto") val efecto: String="",
    @SerializedName("imagen") val imagen: String=""
)
