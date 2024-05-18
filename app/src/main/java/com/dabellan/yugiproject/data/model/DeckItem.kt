package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class DeckItem(
    @SerializedName("id") val id: Int=0,
    @SerializedName("nombre") val nombre: String="",
    @SerializedName("precio") val precio: Double=0.0,
    @SerializedName("id_user") val id_user: Int=0
)
