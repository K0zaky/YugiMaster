package com.dabellan.yugiproject

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//TODO: Cuando Antonio me actualice la API arreglar detalles de cartas


@Composable
fun DetallesCarta(cartaId: String) {
    Text(text = "pepino")
}

/*
@Composable
fun DetallesCarta(cartaId: String): MonstruoItem? {

    val service = RetrofitInstance.api
    cartaId.toInt()
    val monstruo = service.getMonstruo(cartaId)
    if (monstruo != null) {
        return MonstruoItem(monstruo.nombre, monstruo.imagen, "Monstruo")
    }

    val magica = service.getMagica(cartaId)
    if (magica != null) {
        return MagicaItem(magica.nombre, magica.imagen, "Mágica")
    }

    val trampa = service.getTrampa(cartaId)
    if (trampa != null) {
        return TrampaItem(trampa.nombre, trampa.imagen, "Trampa")
    }
}
*/