package com.dabellan.yugiproject


//CREO que no se usa
sealed class Ruta(val ruta: String) {
    object MainActivity: Ruta("cardListScreen")
    object DetallesCarta: Ruta("detalles_carta")
}