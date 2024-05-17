package com.dabellan.yugiproject.data.model

import com.google.gson.annotations.SerializedName

data class UsuarioItem(
    @SerializedName("id") val id: Int=0,
    @SerializedName("nick") val nick: String="",
    @SerializedName("correo") val correo: String="",
    @SerializedName("contrasenya") val contrasenya: String="",
    @SerializedName("imagen") val imagen: String=""
)
