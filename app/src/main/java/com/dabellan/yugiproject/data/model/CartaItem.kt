package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class CartaItem(
    @SerializedName("id") val id: Int=0,
    @SerializedName("nombre") val nombre: String="",
    @SerializedName("codigo") val codigo: String="",
    @SerializedName("precio") val precio: Double=0.0,
    @SerializedName("imagen") val imagen: String=""
)