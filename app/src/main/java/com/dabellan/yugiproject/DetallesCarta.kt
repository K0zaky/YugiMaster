package com.dabellan.yugiproject

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dabellan.yugiproject.Instances.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO: Cuando Antonio me actualice la API arreglar detalles de cartas



@Composable
fun DetallesCarta(cartaId: String) {
    val service = RetrofitInstance.api
    cartaId.toInt()

    val cartaNombre = remember { mutableStateOf<Any?>(null) }
    val cartaImagen = remember { mutableStateOf<Any?>(null) }
    val cartaTipo = remember { mutableStateOf<Any?>(null) }


    LaunchedEffect(Unit) {
        val monstruo = withContext(Dispatchers.IO) {
            runCatching { service.getMonstruo(cartaId) }.getOrNull()
        }
        if (monstruo != null) {
            cartaNombre.value = monstruo.nombre
            cartaImagen.value = monstruo.imagen
            cartaTipo.value = monstruo.tipo_monstruo
            return@LaunchedEffect
        }

        val magica = withContext(Dispatchers.IO) {
            runCatching { service.getMagica(cartaId) }.getOrNull()
        }
        if (magica != null) {
            cartaNombre.value = magica.nombre
            cartaImagen.value = magica.imagen
            cartaTipo.value = magica.tipo_magia
            return@LaunchedEffect
        }

        val trampa = withContext(Dispatchers.IO) {
            runCatching { service.getTrampa(cartaId) }.getOrNull()
        }
        if (trampa != null) {
            cartaNombre.value = trampa.nombre
            cartaImagen.value = trampa.imagen
            cartaTipo.value = trampa.tipo_trampa
            return@LaunchedEffect
        }

        cartaNombre.value = "Carta no encontrada"
    }

    if (cartaNombre.value != null) {
        val imageUrl = cartaImagen.value.toString()?.replace("\\", "")
        Text(text = normalizarTexto(cartaNombre.value.toString()))
        CoilImage(url = imageUrl, modifier = Modifier.size(800.dp))
        //Text(text = normalizarTexto(cartaTipo.value.toString()))
    }
}
