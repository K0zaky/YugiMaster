package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class CartaItem(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("codigo") val codigo: String,
    @SerializedName("precio") val precio: Double,
    @SerializedName("imagen") val imagen: String
)