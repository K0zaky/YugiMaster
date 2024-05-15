package com.dabellan.yugiproject


//CREO que no se usa
sealed class Ruta(val ruta: String) {
    object MainActivity: Ruta("main_activity")
    object DetallesCarta: Ruta("detalles_carta")
}