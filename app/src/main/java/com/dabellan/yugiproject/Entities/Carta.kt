package com.dabellan.yugiproject.Entities

data class Carta(
    var id: Long = 0,
    var nombre: String,
    var codigo: String,
    var precio: Double,
    var imagen: String
)
